package com.ycourlee.tranquil.autoconfiguration.redisson;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.loader.Loader;
import com.mitchellbosecke.pebble.loader.StringLoader;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yongjiang
 * @date 2022.04.14
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({PebbleEngine.class, RedissonClient.class})
public class RedissonAnnotationAutoConfiguration {

    @Bean("pebbleLoader")
    public Loader<?> pebbleLoader() {
        return new StringLoader();
    }

    @Bean
    @ConditionalOnMissingBean
    public RedissonTemplate redissonTemplate(
            @Qualifier("redisson") RedissonClient redissonClient
    ) {
        return new RedissonTemplate(redissonClient);
    }

    @Bean
    @ConditionalOnMissingBean
    public RedissonLockAspect redissonLockAspect(
            RedissonTemplate redissonTemplate,
            @Qualifier("pebbleEngine") PebbleEngine pebbleEngine
    ) {
        return new RedissonLockAspect(redissonTemplate, pebbleEngine);
    }
}
