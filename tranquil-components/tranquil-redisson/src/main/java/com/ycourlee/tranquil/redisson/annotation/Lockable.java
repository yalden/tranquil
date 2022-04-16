package com.ycourlee.tranquil.redisson.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author yongjiang
 * @date 2022.04.03
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Lockable {

    String[] keys();

    long waitTime() default -1;

    long leaseTime() default -1;

    TimeUnit unit() default TimeUnit.SECONDS;
}
