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

    /**
     * 需要获取的锁，支持获取多把锁
     * @return
     */
    String[] keys();

    /**
     * 默认等待1秒
     * @return
     */
    long waitTime() default 1;

    /**
     * 默认10秒释放锁。若要开启续期，需要设为-1，这样redisson会每30秒续期一次（观察狗模式，有消耗，理性使用续期方式）
     * @return
     */
    long leaseTime() default 10;

    /**
     * 默认{@link Lockable#waitTime()}和{@link Lockable#leaseTime()}的时间单位为秒。
     * @return
     */
    TimeUnit unit() default TimeUnit.SECONDS;
}
