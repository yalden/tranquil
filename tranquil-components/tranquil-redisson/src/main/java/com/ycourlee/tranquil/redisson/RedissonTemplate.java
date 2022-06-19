package com.ycourlee.tranquil.redisson;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

/**
 * @author yooonn
 * @date 2022.04.16
 */
public class RedissonTemplate {

    private RedissonClient redissonClient;

    public RedissonTemplate(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public RLock getLock(String name) {
        return redissonClient.getLock(name);
    }

    public RLock getMultiLock(String... names) {
        return redissonClient.getMultiLock(getLocks(names));
    }

    public RLock[] getLocks(String... names) {
        RLock[] rLocks = new RLock[names.length];
        int i = 0;
        for (String name : names) {
            rLocks[i++] = getLock(name);
        }
        return rLocks;
    }
}
