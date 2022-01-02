package com.ycourlee.tranquil.autoconfiguration.crypto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.lang.Nullable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

/**
 * @author jiangyong
 */
@ExtendWith(SpringExtension.class)
@ImportAutoConfiguration
@ContextConfiguration(classes = {
        CryptoAutoConfiguration.class,
        AopAutoConfiguration.class
})
@ComponentScan(basePackages = "com.ycourlee.tranquil.autoconfiguration.crypto")
@SpringBootTest
public class ApplicationContextRunningConfiguration implements ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(ApplicationContextRunningConfiguration.class);

    @Nullable
    protected ApplicationContext applicationContext;

    @Autowired
    protected AesCrypto aesCrypto;

    @BeforeEach
    public void autowiredTest() {
        Assertions.assertNotNull(aesCrypto);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

        Arrays.stream(applicationContext.getBeanDefinitionNames()).sorted().forEach(log::info);
    }
}
