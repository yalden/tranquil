package com.ycourlee.tranquil.autoconfiguration.redisson;

import com.mitchellbosecke.pebble.boot.autoconfigure.PebbleAutoConfiguration;
import com.mitchellbosecke.pebble.loader.Loader;
import com.ycourlee.tranquil.autoconfiguration.AbstractAutoConfigurationTests;
import com.ycourlee.tranquil.redisson.RedissonTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author yooonn
 * @date 2022.06.19
 */
@ContextConfiguration(classes = {
        RedissonAutoConfiguration.class,
        PebbleAutoConfiguration.class,
        RedisAutoConfiguration.class,
        RedissonAnnotationAutoConfiguration.class,
        RedissonAnnotationAutoConfigurationTests.BeanRegistrar.class
})
public class RedissonAnnotationAutoConfigurationTests extends AbstractAutoConfigurationTests {

    @Autowired
    @Qualifier("pebbleLoader")
    public  Loader<?>        loader;
    @Autowired
    private RedissonTemplate redissonTemplate;
    @Autowired
    private LockableAspect   lockableAspect;

    @BeforeEach
    public void beanTest() {
        assertNotNull(redissonTemplate);
        assertNotNull(lockableAspect);
        assertNotNull(loader);
    }

    @Configuration
    public static class BeanRegistrar {

        @Bean
        public LockableTests lockableTests(StringRedisTemplate redisTemplate) {
            return new LockableTests(redisTemplate);
        }
    }
}
