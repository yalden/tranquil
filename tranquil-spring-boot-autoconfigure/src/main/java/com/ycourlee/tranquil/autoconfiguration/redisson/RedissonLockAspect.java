package com.ycourlee.tranquil.autoconfiguration.redisson;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.ycourlee.tranquil.core.util.Assert;
import com.ycourlee.tranquil.redisson.RedissonTemplate;
import com.ycourlee.tranquil.redisson.annotation.Lockable;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yongjiang
 * @date 2022.04.03
 */
@Aspect
public class RedissonLockAspect {

    private static final Logger log = LoggerFactory.getLogger(RedissonLockAspect.class);

    private RedissonTemplate redissonTemplate;
    private PebbleEngine     pebbleEngine;

    public RedissonLockAspect(
            RedissonTemplate redissonTemplate,
            PebbleEngine pebbleEngine
    ) {
        this.redissonTemplate = redissonTemplate;
        this.pebbleEngine = pebbleEngine;
    }

    @Around(value = "@annotation(lockableAnno)", argNames = "joinPoint,lockableAnno")
    public Object lockableInterceptor(ProceedingJoinPoint joinPoint, Lockable lockableAnno) throws Throwable {
        RLock lock;

        String[] keys = parseKeys(lockableAnno.keys(), joinPoint);
        if (keys.length == 0) {
            log.warn("{} used @Lockable but no key.", joinPoint.getSignature().toShortString());
            return joinPoint.proceed();
        }
        if (keys.length == 1) {
            lock = redissonTemplate.getLock(keys[0]);
        } else {
            lock = redissonTemplate.getMultiLock(keys);
        }
        return proceedingWhenLocked(joinPoint, keys, lock, lockableAnno);
    }

    private Object proceedingWhenLocked(ProceedingJoinPoint joinPoint, String[] keys, RLock lock, Lockable lockableAnno) throws Throwable {
        String joinPointName = joinPoint.getSignature().toShortString();
        String keysString = Arrays.toString(keys);
        if (lock.tryLock(lockableAnno.waitTime(), lockableAnno.leaseTime(), lockableAnno.unit())) {
            log.info("{} acquired lock {}", joinPointName, keysString);
            try {
                return joinPoint.proceed();
            } finally {
                lock.unlock();
            }
        } else {
            log.info("{} wait {} time({}) and give up the lock {}",
                    joinPointName, lockableAnno.waitTime(), lockableAnno.unit(), keysString);
            throw new WaitLockTimeoutException(joinPointName + " wait " + keysString + " timeout.");
        }
    }

    private String[] parseKeys(String[] keys, ProceedingJoinPoint joinPoint) throws IOException {
        if (keys.length == 0) {
            return keys;
        }
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        Map<String, Object> Map = new HashMap<>();
        Assert.that(parameterNames.length == args.length, "args error");
        for (int i = 0; i < parameterNames.length; i++) {
            Map.put(parameterNames[i], args[i]);
        }
        String[] parsedKeys = new String[keys.length];
        for (int i = 0; i < keys.length; i++) {
            Writer writer = new StringWriter();
            pebbleEngine.getTemplate(keys[i]).evaluate(writer, Map);
            String value = writer.toString();
            Assert.notBlank(value, "args error");
            parsedKeys[i] = value;
        }
        return parsedKeys;
    }

}
