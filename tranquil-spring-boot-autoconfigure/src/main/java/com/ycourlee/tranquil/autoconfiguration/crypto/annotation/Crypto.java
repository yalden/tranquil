package com.ycourlee.tranquil.autoconfiguration.crypto.annotation;

import java.lang.annotation.*;

/**
 * @author yongjiang
 * @date 2021.12.16
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Crypto {

    String[] enableGroups() default {""};
}
