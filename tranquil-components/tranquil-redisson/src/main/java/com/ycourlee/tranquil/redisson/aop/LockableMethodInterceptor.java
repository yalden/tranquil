package com.ycourlee.tranquil.redisson.aop;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.ycourlee.tranquil.redisson.RedissonTemplate;
import com.ycourlee.tranquil.redisson.annotation.Lockable;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author yongjiang
 * @date 2022.06.22
 */
public class LockableMethodInterceptor implements MethodInterceptor, BeanFactoryAware, SmartInitializingSingleton {

    private static final Logger log = LoggerFactory.getLogger(LockableMethodInterceptor.class);

    public static final ParameterNameDiscoverer PARAMETER_NAME_DISCOVERER = new DefaultParameterNameDiscoverer();

    private RedissonTemplate redissonTemplate;
    private PebbleEngine     pebbleEngine;
    private BeanFactory      beanFactory;

    public LockableMethodInterceptor(RedissonTemplate redissonTemplate, @Nullable PebbleEngine pebbleEngine) {
        this.redissonTemplate = redissonTemplate;
        this.pebbleEngine = pebbleEngine;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Class<?> ultimate = AopProxyUtils.ultimateTargetClass(invocation.getThis());
        if (!invocation.getThis().getClass().equals(ultimate)) {
            return invocation.proceed();
        }
        Method method = invocation.getMethod();
        AnnotationAttributes attributes = AnnotatedElementUtils.findMergedAnnotationAttributes(method, Lockable.class,
                false, false);
        if (attributes == null) {
            return invocation.proceed();
        }
        List<String> keys = parseKey(attributes.getStringArray("keys"), invocation.getArguments(), method);
        if (keys.size() == 0) {
            log.warn("{} used @Lockable but no key.", method.getName());
            return invocation.proceed();
        }
        return redissonTemplate.executeInLock(keys,
                attributes.getNumber("waitTime").longValue(),
                attributes.getNumber("leaseTime").longValue(),
                () -> {
                    try {
                        return invocation.proceed();
                    } catch (Throwable e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    protected List<String> parseKey(String[] originKeys, Object[] args, Method lockableMethod) throws IOException {
        Assert.notNull(originKeys, "originKeys must not be null");
        Assert.notNull(args, "args must not be null");
        Assert.notNull(lockableMethod, "lockableMethod must not be null");
        if (originKeys.length == 0) {
            return Collections.emptyList();
        }
        Map<String, Object> paramNameWithValue = computeParamValueMap(args, lockableMethod);
        if (paramNameWithValue.isEmpty()) {
            return Arrays.asList(originKeys);
        }
        return generate(originKeys, paramNameWithValue);
    }

    protected List<String> generate(String[] originKeys, Map<String, Object> paramNameWithValue) throws IOException {
        List<String> generatedKeys = new ArrayList<>(originKeys.length);
        for (String originKey : originKeys) {
            Writer writer = new StringWriter();
            pebbleEngine.getTemplate(originKey).evaluate(writer, paramNameWithValue);
            String value = writer.toString();
            Assert.hasLength(value, "illegal generated key: " + value);
            generatedKeys.add(value);
        }
        return generatedKeys;
    }

    protected Map<String, Object> computeParamValueMap(Object[] args, Method lockableMethod) {
        String[] paramNames = PARAMETER_NAME_DISCOVERER.getParameterNames(lockableMethod);
        if (paramNames == null || paramNames.length == 0) {
            return Collections.emptyMap();
        }
        Map<String, Object> Map = new HashMap<>();
        for (int i = 0; i < paramNames.length; i++) {
            Map.put(paramNames[i], args[i]);
        }
        return Map;
    }

    @Override
    public void setBeanFactory(@Nonnull BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void afterSingletonsInstantiated() {
        pebbleEngine = beanFactory.getBean(PebbleEngine.class);
        Assert.notNull(pebbleEngine, "lockable Method Interceptor initializing error");
    }
}
