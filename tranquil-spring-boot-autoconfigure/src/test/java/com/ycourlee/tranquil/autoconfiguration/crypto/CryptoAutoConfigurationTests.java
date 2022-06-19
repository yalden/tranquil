package com.ycourlee.tranquil.autoconfiguration.crypto;

import com.ycourlee.tranquil.autoconfiguration.AbstractAutoConfigurationTests;
import com.ycourlee.tranquil.autoconfiguration.crypto.aspect.CryptoAspectPointcutTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.PostConstruct;
import java.util.Arrays;

/**
 * @author yooonn
 */
@ContextConfiguration(classes = {
        CryptoAutoConfiguration.class,
        CryptoAutoConfigurationTests.BeanRegistrar.class
})
public class CryptoAutoConfigurationTests extends AbstractAutoConfigurationTests {

    private static final Logger log = LoggerFactory.getLogger(CryptoAutoConfigurationTests.class);

    @Autowired
    protected AesCrypto aesCrypto;

    @BeforeEach
    public void autowiredTest() {
        Assertions.assertNotNull(aesCrypto);
    }

    @PostConstruct
    public void beansExpose() {
        Arrays.stream(applicationContext.getBeanDefinitionNames()).sorted().forEach(log::info);
    }

    @Configuration
    public static class BeanRegistrar {

        @Bean
        public CryptoAspectPointcutTest cryptoAspectPointcutTest() {
            return new CryptoAspectPointcutTest();
        }
    }
}
