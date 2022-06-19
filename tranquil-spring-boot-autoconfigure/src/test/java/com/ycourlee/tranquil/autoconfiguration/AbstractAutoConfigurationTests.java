package com.ycourlee.tranquil.autoconfiguration;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.BeansException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.lang.NonNull;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author yooonn
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AbstractAutoConfigurationTests.class)
@EnableAspectJAutoProxy
public class AbstractAutoConfigurationTests implements ApplicationContextAware {

    protected ConfigurableApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        if (applicationContext instanceof ConfigurableApplicationContext) {
            this.applicationContext = ((ConfigurableApplicationContext) applicationContext);
            return;
        }
        throw new IllegalStateException();
    }
}
