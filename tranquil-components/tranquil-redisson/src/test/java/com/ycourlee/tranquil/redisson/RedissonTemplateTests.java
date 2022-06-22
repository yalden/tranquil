package com.ycourlee.tranquil.redisson;

import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author yongjiang
 * @date 2022.06.22
 */
public class RedissonTemplateTests {

    private static final Logger log = LoggerFactory.getLogger(RedissonTemplateTests.class);

    static RedissonTemplate template;

    static {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6382");
        template = new RedissonTemplate(Redisson.create(config));
    }

    /**
     * 简单用法
     */
    @Test
    void simpleUseTest() {
        // 对于单锁, wait time为0 -1 -2, 均会尝试锁一次, 即 <= 0
        template.executeInLock(Collections.singleton("Umbrella:rain"), 0, () -> null);
        template.executeInLock(Collections.singleton("Umbrella:rain"), -1, () -> null);
        template.executeInLock(Collections.singleton("Umbrella:rain"), -2, () -> null);
        //  对于联锁, 注意它实现leaseTime的细节, 只有 -1 才会尝试锁一次, 0 2 在lua的pExpire阶段就被立即释放了
        assertThrows(WaitLockTimeoutException.class, () -> {
            template.executeInLock(Arrays.asList("Umbrella:rain", "Umbrella:water"), 0, () -> null);
        });
        template.executeInLock(Arrays.asList("Umbrella:rain", "Umbrella:water"), -1, () -> null);

        assertThrows(WaitLockTimeoutException.class, () -> {
            template.executeInLock(Arrays.asList("Umbrella:rain", "Umbrella:water"), -2, () -> null);
        });
    }

    /**
     * 测试waitTime; 若waitTime <= 0则只会获取一次
     *
     * @throws InterruptedException
     */
    @Test
    void waitTimeTest() throws InterruptedException {
        RLock opt = template.getLock("manual opt");
        AtomicBoolean flag = new AtomicBoolean(false);
        if (opt.tryLock(5, -1, TimeUnit.SECONDS)) {
            try {
                List<Future<?>> futures = new ArrayList<>();
                ExecutorService executorService = Executors.newScheduledThreadPool(100);
                // 线程1, 获取锁后假装处理逻辑
                futures.add(executorService.submit(() -> {
                    template.executeInLock(Collections.singleton("Umbrella:rain"), -1, () -> {
                        log.info("{} acquired lock sleep {}s", "foo", 3);
                        flag.compareAndSet(false, true);
                        quiteSleep(8);
                        return null;
                    });
                }));
                // 后续线程尝试获取锁
                // 5个线程不放弃, 等待
                for (int i = 0; i < 10; i++) {
                    futures.add(executorService.submit(() -> {
                        while (!flag.get()) {
                        }
                        template.executeInLock(Collections.singleton("Umbrella:rain"), 20, () -> {
                            log.info("{} acquired lock sleep {}s", Thread.currentThread().getName(), 1);
                            quiteSleep(1);
                            return null;
                        });
                    }));
                }

                // 10个线程不乐意等
                for (int i = 0; i < 10; i++) {
                    futures.add(executorService.submit(() -> {
                        try {
                            while (!flag.get()) {
                            }
                            template.executeInLock(Collections.singleton("Umbrella:rain"), () -> {
                                throw new IllegalStateException("cannot enter there.");
                            });
                        } catch (WaitLockTimeoutException e) {
                            log.info("{} wait {}s timeout", Thread.currentThread().getName(), 0);
                        }
                    }));
                }
                futures.forEach(f -> {
                    try {
                        f.get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

            } finally {
                opt.unlock();
            }
        }
    }

    private void quiteSleep(int second) {
        try {
            Thread.sleep(second * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
