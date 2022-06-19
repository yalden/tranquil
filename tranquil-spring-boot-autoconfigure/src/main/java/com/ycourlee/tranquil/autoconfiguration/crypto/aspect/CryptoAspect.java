package com.ycourlee.tranquil.autoconfiguration.crypto.aspect;

import com.ycourlee.tranquil.autoconfiguration.crypto.AesCrypto;
import com.ycourlee.tranquil.crypto.annotation.Crypto;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 在标注{@link Crypto}注解的方法return前，进行加密/解密
 *
 * @author yooonn
 * @date 2021.12.06
 * @see com.ycourlee.tranquil.crypto.annotation.Ciphertext
 * @see com.ycourlee.tranquil.crypto.annotation.Plaintext
 */
@Aspect
public class CryptoAspect {

    private static final Logger log = LoggerFactory.getLogger(CryptoAspect.class);

    private CryptoAnnotationProcess cipher;
    private CryptoAnnotationProcess plain;

    public CryptoAspect(AesCrypto aesCrypto) {
        this.cipher = new CiphertextProcessor(aesCrypto);
        this.plain = new PlaintextProcessor(aesCrypto);
    }

    @Around(value = "@annotation(crypto)",
            argNames = "joinPoint,crypto")
    public Object aroundCrypto(ProceedingJoinPoint joinPoint, Crypto crypto) {
        String name = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            log.error("Joint point proceed error, ignore aspect processing, name: {}, args: {}", name, Arrays.toString(args), e);
            throw new RuntimeException(e);
        }
        Class<?> resultClass = result.getClass();
        if (!ClassUtils.isPrimitiveOrWrapper(resultClass)) {
            List<String> enableGroups = Arrays.asList(crypto.enableGroups());
            cipher.processInput(result, enableGroups);
            plain.processInput(result, enableGroups);
        }
        return result;
    }
}
