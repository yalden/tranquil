package com.ycourlee.tranquil.autoconfiguration.crypto;

import com.ycourlee.tranquil.autoconfiguration.crypto.aspect.CryptoAspect;
import com.ycourlee.tranquil.crypto.aes.AesCryptoExecutor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yooonn
 * @date 2021.12.22
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnCryptoEnabled
@ConditionalOnClass({AesCryptoExecutor.class})
class AnnotationDrivenAesCryptoConfiguration {

    private CryptoProperties  cryptoProperties;
    private AesCryptoExecutor aesCryptoExecutor;

    AnnotationDrivenAesCryptoConfiguration(CryptoProperties cryptoProperties, AesCryptoExecutor aesCryptoExecutor) {
        this.cryptoProperties = cryptoProperties;
        this.aesCryptoExecutor = aesCryptoExecutor;
    }

    @Bean
    @ConditionalOnMissingBean
    AesCrypto aesCrypto() {
        return new AesCrypto(cryptoProperties, aesCryptoExecutor);
    }

    @Bean
    @ConditionalOnMissingBean
    CryptoAspect cryptoAspect(AesCrypto aesCrypto) {
        return new CryptoAspect(aesCrypto);
    }
}