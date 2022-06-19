package com.ycourlee.tranquil.autoconfiguration.redisson;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.boot.autoconfigure.PebbleProperties;
import com.mitchellbosecke.pebble.loader.Loader;
import com.mitchellbosecke.pebble.loader.StringLoader;
import com.ycourlee.tranquil.redisson.RedissonTemplate;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yooonn
 * @date 2022.04.14
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnBean({PebbleEngine.class, RedissonClient.class})
@ConditionalOnProperty(prefix = "tranquil.redisson", name = "enable", havingValue = "true", matchIfMissing = true)
public class RedissonAnnotationAutoConfiguration {

    /**
     * Should allow override bean. via {@code spring.main.allow-bean-definition-overriding=true}
     *
     * @return
     */
    @Bean("pebbleLoader")
    public Loader<?> pebbleLoader(PebbleProperties properties) {
        StringLoader loader = new StringLoader();
        loader.setPrefix(properties.getPrefix());
        loader.setSuffix(properties.getSuffix());
        loader.setCharset(properties.getCharsetName());
        return loader;
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
    public LockableAspect redissonLockAspect(
            RedissonTemplate redissonTemplate,
            @Qualifier("pebbleEngine") PebbleEngine pebbleEngine
    ) {
        return new LockableAspect(redissonTemplate, pebbleEngine);
    }
}
