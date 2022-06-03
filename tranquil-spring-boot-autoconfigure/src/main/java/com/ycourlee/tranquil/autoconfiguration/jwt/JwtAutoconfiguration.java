package com.ycourlee.tranquil.autoconfiguration.jwt;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yoooonn
 * @date 2022.06.04
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(JwtProperties.class)
public class JwtAutoconfiguration {

    @Bean
    public JwtEnhancer jwtEnhancer(JwtProperties jwtProperties) {
        return new JwtEnhancer(jwtProperties);
    }
}
