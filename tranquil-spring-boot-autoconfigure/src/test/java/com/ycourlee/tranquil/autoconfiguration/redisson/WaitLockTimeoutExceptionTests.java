package com.ycourlee.tranquil.autoconfiguration.redisson;

import com.ycourlee.tranquil.core.CommonConstants;
import com.ycourlee.tranquil.redisson.WaitLockTimeoutException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.StopWatch;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author yooonn
 * @date 2022.06.19
 */
@Disabled("High concurrency tests require high hardware performance to run")
@TestPropertySource(properties = {
        "logging.level.com.ycourlee.tranquil.redisson.RedissonTemplate = trace"
})
public class WaitLockTimeoutExceptionTests extends RedissonAnnotationAutoConfigurationTests {

    private static final Logger log = LoggerFactory.getLogger(LockableTests.class);

    @Autowired
    private LockableTests lockableTests;

    @Test
    void multiLockTest() throws InterruptedException {
        int threadCnt = 100;
        Long sharedA = lockableTests.sharedAToZero();
        Long sharedB = lockableTests.sharedBToZero();
        CountDownLatch localLatch = new CountDownLatch(threadCnt * 3);
        for (int i = 0; i < threadCnt; i++) {
            new Thread(() -> {
                lockableTests.incrAInLock();
                localLatch.countDown();
            }).start();
            new Thread(() -> {
                lockableTests.incrBInLock();
                localLatch.countDown();
            }).start();
            new Thread(() -> {
                lockableTests.incrABInLock();
                localLatch.countDown();
            }).start();
        }
        localLatch.await();
        assertEquals(sharedA + 2 * threadCnt, lockableTests.sharedA());
        assertEquals(sharedB + 2 * threadCnt, lockableTests.sharedB());
    }

    @Test
    void noLockTest() throws InterruptedException {
        boolean fullSuccess = true;
        for (int c = 0; c < CommonConstants.TEST_CASE_TEN; c++) {
            int threadCnt = 10000;
            Long sharedA = lockableTests.sharedAToZero();
            CountDownLatch localLatch = new CountDownLatch(threadCnt);
            for (int i = 0; i < threadCnt; i++) {
                new Thread(() -> {
                    lockableTests.incrA();
                    localLatch.countDown();
                }).start();
            }
            localLatch.await();
            if (!Objects.equals(sharedA + 100, lockableTests.sharedA())) {
                fullSuccess = false;
            }
        }
        assertFalse(fullSuccess);
    }

    /**
     * 200线程对同一操作数进行加1操作，循环10次，使用不同的同步方式，总耗时如下<br />
     * <p/>
     *
     * <tb>
     * <tr>
     * <th>同步方式</th>
     * <th>耗时（ms/per 200T）</th>
     * </tr>
     * <tr>
     * <td>无锁</td>
     * <td>24.1</td>
     * </tr>
     * <tr>
     * <td>synchronized method</td>
     * <td>21.5</td>
     * </tr>
     * <tr>
     * <td>lockable annotation</td>
     * <td>26.6</td>
     * </tr>
     * <tr>
     * <td>set nx</td>
     * <td>1731.1</td>
     * </tr>
     * </tb>
     *
     * @throws InterruptedException
     */
    @Test
    void benchmarkTest() throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        run(o -> lockableTests.incrA(), "not concurrency", stopWatch);
        run(o -> lockableTests.incrABySetNx(), "setNx", stopWatch);
        run(o -> lockableTests.incrABySyncMethod(), "synchronized", stopWatch);
        run(o -> lockableTests.incrAInLock(), "lock", stopWatch);
        log.info("stopWatch.prettyPrint(): \n{}", stopWatch.prettyPrint());
    }

    private void run(Consumer<Object> consumer, String taskName, StopWatch stopWatch) throws InterruptedException {
        stopWatch.start(taskName);
        for (int j = 0; j < 10; j++) {
            CountDownLatch localLatch = new CountDownLatch(200);
            for (int i = 0; i < 200; i++) {
                new Thread(() -> {
                    consumer.accept(null);
                    localLatch.countDown();
                }).start();
            }
            localLatch.await();
        }
        stopWatch.stop();
    }
}
