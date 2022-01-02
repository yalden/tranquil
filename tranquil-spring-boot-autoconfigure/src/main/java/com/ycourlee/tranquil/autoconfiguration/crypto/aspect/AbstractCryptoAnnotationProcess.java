package com.ycourlee.tranquil.autoconfiguration.crypto.aspect;

import com.ycourlee.tranquil.autoconfiguration.crypto.AesCrypto;
import com.ycourlee.tranquil.autoconfiguration.crypto.annotation.CryptoAnnotationMetadata;
import com.ycourlee.tranquil.crypto.Algorithms;
import com.ycourlee.tranquil.crypto.exception.CryptoException;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yongjiang
 * @date 2021.12.17
 */
abstract class AbstractCryptoAnnotationProcess implements CryptoAnnotationProcess {

    protected AesCrypto aesCrypto;

    public AbstractCryptoAnnotationProcess(AesCrypto aesCrypto) {
        this.aesCrypto = aesCrypto;
    }

    protected void processField(Object obj, Field field, CryptoAnnotationMetadata annoMeta) {
        Pair<Method, Method> accessors = javaBeanFieldAccessors(obj.getClass(), field);
        Object value = ReflectionUtils.invokeMethod(accessors.getLeft(), obj);
        if (value != null) {
            ReflectionUtils.invokeMethod(accessors.getRight(), obj, transfer(((String) value), annoMeta));
        }
    }

    protected Map<Field, Annotation> findAnnotatedFields(Class<?> targetClass, Class<? extends Annotation> cryptoAnnoClass) {
        Field[] declaredFields = targetClass.getDeclaredFields();
        Map<Field, Annotation> fields = new HashMap<>();
        for (Field field : declaredFields) {
            Annotation annotation = field.getAnnotation(cryptoAnnoClass);
            fields.put(field, annotation);
        }
        return fields;
    }

    protected String transfer(String content, CryptoAnnotationMetadata annoMeta) {
        String result;
        if (Algorithms.AES.equals(annoMeta.getAlgorithm())) {
            if (StringUtils.isEmpty(annoMeta.getKeyGroup())) {
                result = annoMeta.getWasCipher() ? aesCrypto.ciphertext(content, annoMeta.getUrlSafely()) :
                        aesCrypto.plaintext(content, annoMeta.getUrlSafely());
            } else {
                result = annoMeta.getWasCipher() ? aesCrypto.ciphertext(content, annoMeta.getKeyGroup(), annoMeta.getUrlSafely()) :
                        aesCrypto.plaintext(content, annoMeta.getKeyGroup(), annoMeta.getUrlSafely());
            }
        } else {
            throw new CryptoException("Beta version, only support Cipher Algorithm AES now");
        }
        return result;
    }

    /**
     * (get, set)
     *
     * @param beanClass beanClass
     * @param field     field
     * @return
     */
    protected Pair<Method, Method> javaBeanFieldAccessors(Class<?> beanClass, Field field) {
        Method getMethod = ReflectionUtils.findMethod(beanClass, "get" + upperCaseLeaderChar(field.getName()));
        Method setMethod = ReflectionUtils.findMethod(beanClass, "set" + upperCaseLeaderChar(field.getName()), String.class);
        Assert.notNull(getMethod, "cannot find get method of field [" +
                field.getName() + "] on bean [" +
                beanClass.getName() + "] when cipher the field");
        Assert.notNull(setMethod, "cannot find set method of field [" +
                field.getName() + "] on bean [" +
                beanClass.getName() + "] when cipher the field");
        return new ImmutablePair<>(getMethod, setMethod);
    }

    protected String upperCaseLeaderChar(String chars) {
        Assert.hasLength(chars, "Chars must be have length");
        return chars.substring(0, 1).toUpperCase() + chars.substring(1);
    }

    protected boolean shouldBeIgnore(String group, List<String> enableGroups) {
        Assert.notEmpty(enableGroups, "enable group cannot be empty");
        if (StringUtils.isEmpty(group)) {
            return false;
        }
        return !enableGroups.contains(group);
    }
}
