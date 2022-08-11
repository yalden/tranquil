package com.ycourlee.tranquil.redisson.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * 方法锁, 可派生.
 *
 * @author yooonn
 * @date 2022.04.03
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface Lockable {

    /**
     * 需要获取的锁, 支持获取联锁
     *
     * @return 锁名称
     */
    @AliasFor("value")
    String[] keys() default {};

    /**
     * 需要获取的锁, 支持获取联锁
     *
     * @return 锁名称
     */
    @AliasFor("keys")
    String[] value() default {};

    /**
     * 默认不等待
     *
     * @return 等待获取锁的时间
     */
    long waitTime() default -1;

    /**
     * 该因子可使waitTime上浮{@link ThreadLocalRandom#nextLong(long)}, 其中bound参数为waitFactor.
     * 默认不上浮
     *
     * @return 等待锁时间上浮因子
     */
    long waitFactor() default 0;

    /**
     * 默认最大10秒释放锁. 若要开启续期, 需要设为-1, 这样redisson会每30秒续期一次（观察狗模式, 有消耗, 理性使用续期方式）
     *
     * @return 锁持有时间
     */
    long leaseTime() default 10;

    /**
     * 默认{@link Lockable#waitTime()}, {@link Lockable#waitFactor()}和{@link Lockable#leaseTime()}的时间单位为秒
     *
     * @return 时间单位
     */
    TimeUnit unit() default TimeUnit.SECONDS;
}
