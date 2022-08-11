package com.ycourlee.tranquil.redisson;

import com.ycourlee.tranquil.redisson.annotation.Lockable;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @author yooonn
 * @date 2022.04.16
 */
public class RedissonTemplate {

    private static final Logger log = LoggerFactory.getLogger(RedissonTemplate.class);

    private RedissonClient redissonClient;

    public RedissonTemplate(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    /**
     * 获取单锁
     *
     * @param name 锁名称, 不可空
     * @return RedissonLock
     */
    public RLock getLock(String name) {
        Assert.hasLength(name, "name must has length");
        return redissonClient.getLock(name);
    }

    /**
     * 获取联锁, 当只有一个name时, 退化为单锁
     *
     * @param names key names
     * @return RedissonMultiLock
     */
    public RLock getMultiLock(String... names) {
        Assert.isTrue(names.length >= 1, "names at least 1");
        if (names.length == 1) {
            return getLock(names[0]);
        }
        return redissonClient.getMultiLock(getLocks(names));
    }

    public RLock getMultiLock(Collection<String> names) {
        return getMultiLock(names.toArray(new String[0]));
    }

    public RLock[] getLocks(String... names) {
        RLock[] rLocks = new RLock[names.length];
        int i = 0;
        for (String name : names) {
            try {
                rLocks[i++] = getLock(name);
            } catch (Exception e) {
                rLocks = null;
                throw e;
            }
        }
        return rLocks;
    }

    public <T> T executeInLock(Collection<String> keyNames, long waitTime, Supplier<T> supplier) {
        return executeInLock(keyNames, waitTime, 10, TimeUnit.SECONDS, supplier);
    }

    public <T> T executeInLock(Collection<String> keyNames, Supplier<T> supplier) {
        return executeInLock(keyNames, -1, 10, TimeUnit.SECONDS, supplier);
    }

    public <T> T executeInLock(Collection<String> keyNames, long waitTime, long leaseTime, Supplier<T> supplier) {
        return executeInLock(keyNames, waitTime, leaseTime, TimeUnit.SECONDS, supplier);
    }

    /**
     * 在给定的锁内, 执行并返回supplier的结果
     *
     * @param keyNames  锁名称, 至少一个
     * @param waitTime  锁等待时间. -1: 不等待, 只尝试获取一次
     * @param leaseTime 倒计leaseTime, 到0则释放锁, 不考虑supplier. -1: 开启锁续期; 其他,
     * @param unit      时间单位
     * @param supplier  获取到锁后的supplier
     * @param <T>       supplier返回值类型
     * @return result of supplier
     * @see Lockable
     */
    public <T> T executeInLock(Collection<String> keyNames, long waitTime, long leaseTime, TimeUnit unit, Supplier<T> supplier) {
        Assert.notEmpty(keyNames, "give one key at least.");
        RLock lock = getMultiLock(keyNames);
        String keyNameString = keyNames.toString();
        try {
            if (lock.tryLock(waitTime, leaseTime, unit)) {
                log.debug("locked {}", keyNames);
                try {
                    return supplier.get();
                } finally {
                    lock.unlock();
                    log.debug("unlocked {}", keyNameString);
                }
            } else {
                log.debug("wait {} seconds but not acquired, give up: {}",
                        waitTime, keyNameString);
                throw new WaitLockTimeoutException("wait lock " + keyNameString + " timeout.", keyNames);
            }
        } catch (InterruptedException e) {
            log.error("interrupted exception as runtime exception");
            throw new RuntimeException(e);
        }
    }
}
