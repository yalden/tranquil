package com.ycourlee.tranquil.crypto.annotation;

import java.lang.annotation.*;

/**
 * @author yooonn
 * @date 2021.12.16
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Crypto {

    String[] enableGroups() default {""};
}
