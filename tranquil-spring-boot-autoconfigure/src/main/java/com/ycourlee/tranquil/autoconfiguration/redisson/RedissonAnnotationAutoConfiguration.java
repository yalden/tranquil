package com.ycourlee.tranquil.autoconfiguration.redisson;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.boot.autoconfigure.PebbleAutoConfiguration;
import com.mitchellbosecke.pebble.boot.autoconfigure.PebbleProperties;
import com.mitchellbosecke.pebble.loader.Loader;
import com.mitchellbosecke.pebble.loader.StringLoader;
import com.ycourlee.tranquil.redisson.RedissonTemplate;
import com.ycourlee.tranquil.redisson.annotation.Lockable;
import com.ycourlee.tranquil.redisson.aop.LockableAnnotationAdvisor;
import com.ycourlee.tranquil.redisson.aop.LockableMethodInterceptor;
import org.redisson.api.RedissonClient;
import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * @author yooonn
 * @date 2022.04.14
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnBean({RedissonClient.class})
@AutoConfigureAfter({RedissonAutoConfiguration.class})
@ConditionalOnProperty(prefix = "tranquil.redisson", name = "enable", havingValue = "true", matchIfMissing = true)
@EnableAspectJAutoProxy
public class RedissonAnnotationAutoConfiguration {

    /**
     * Override loader bean of {@link PebbleAutoConfiguration#pebbleLoader(com.mitchellbosecke.pebble.boot.autoconfigure.PebbleProperties)}
     * Should allow override bean. via {@code spring.main.allow-bean-definition-overriding=true}
     *
     * @return pebble string loader
     */
    @Bean("pebbleLoader")
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @ConditionalOnClass(PebbleEngine.class)
    public Loader<?> pebbleLoader(PebbleProperties properties) {
        StringLoader loader = new StringLoader();
        loader.setPrefix(properties.getPrefix());
        loader.setSuffix(properties.getSuffix());
        loader.setCharset(properties.getCharsetName());
        return loader;
    }

    @Bean
    @ConditionalOnMissingBean
    public RedissonTemplate tranquilRedissonTemplate(
            @Qualifier("redisson") RedissonClient redissonClient
    ) {
        return new RedissonTemplate(redissonClient);
    }

    @Bean
    @ConditionalOnMissingBean
    public Pointcut tranquilRedissonDefaultPointcut() {
        return new AnnotationMatchingPointcut(null, Lockable.class, true);
    }

    /**
     * 默认使用pebble生成key. 当然, 也可以继承{@link LockableMethodInterceptor}
     * 重写其中生成key的方法, 再override这个bean
     *
     * @param redissonTemplate redisson template
     * @param pebbleEngine     pebble engine
     * @return lockable interceptor
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(name = "pebbleEngine")
    public LockableMethodInterceptor tranquilLockableMethodInterceptor(
            RedissonTemplate redissonTemplate,
            @Qualifier("pebbleEngine") PebbleEngine pebbleEngine
    ) {
        return new LockableMethodInterceptor(redissonTemplate, pebbleEngine);
    }

    @Bean
    @ConditionalOnMissingBean
    public LockableAnnotationAdvisor lockableAnnotationAdvisor(
            LockableMethodInterceptor interceptor,
            Pointcut pointcut
    ) {
        return new LockableAnnotationAdvisor(interceptor, pointcut);
    }
}
