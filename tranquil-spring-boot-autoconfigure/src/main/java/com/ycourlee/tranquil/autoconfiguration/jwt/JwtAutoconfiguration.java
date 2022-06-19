package com.ycourlee.tranquil.autoconfiguration.jwt;

import com.ycourlee.tranquil.jwt.JwtHelper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yooonn
 * @date 2022.06.04
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(JwtHelper.class)
@EnableConfigurationProperties(JwtProperties.class)
@ConditionalOnProperty(prefix = JwtProperties.PREFIX, name = "enable", havingValue = "true", matchIfMissing = true)
public class JwtAutoconfiguration {

    @Bean
    public JwtEnhancer jwtEnhancer(JwtProperties jwtProperties) {
        return new JwtEnhancer(jwtProperties);
    }
}
