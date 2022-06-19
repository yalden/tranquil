package com.ycourlee.tranquil.autoconfiguration.redisson;

import com.ycourlee.tranquil.redisson.annotation.Lockable;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author yooonn
 * @date 2022.06.19
 */
public class LockableTests {

    private static long sharedA = 0;
    private static long sharedB = 100;

    private StringRedisTemplate redisTemplate;

    public LockableTests(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Lockable(keys = "shared:num:incr", waitTime = 5)
    public void incrAInLock() {
        sharedA++;
    }

    @Lockable(keys = "shared:num:incr", waitTime = 0)
    public void incrAInLockNoWait() {
        sharedA++;
    }

    @Lockable(keys = "shared:num1:incr", waitTime = 5)
    public void incrBInLock() {
        sharedB++;
    }

    @Lockable(keys = {"shared:num:incr", "shared:num1:incr",}, waitTime = 5)
    public void incrABInLock() {
        sharedA++;
        sharedB++;
    }

    public void incrABySetNx() {
        while (true) {
            // noinspection ConstantConditions
            if (redisTemplate.opsForValue().setIfAbsent("shared:num:incr", "1")) {
                sharedA++;
                redisTemplate.delete("shared:num:incr");
                break;
            }
        }
    }

    public synchronized void incrABySyncMethod() {
        sharedA++;
    }

    public void incrA() {
        sharedA++;
    }

    public Long sharedA() {
        return sharedA;
    }

    public Long sharedB() {
        return sharedB;
    }

    public Long sharedAToZero() {
        return sharedA = 0;
    }

    public Long sharedBToZero() {
        return sharedB = 0;
    }
}
