package com.mrgao.thread.semaphore;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author Mr.Gao
 * @date 2024/8/16 17:04
 * @apiNote:
 */
@Slf4j
public class SemaphoreExample {

    private static final int threadCount = 200;

    public static void main(String[] args) {
        // 创建线程池
        ExecutorService exec = Executors.newCachedThreadPool();

        // 创建 Semaphore对象
        Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            exec.execute(() -> {
                try {
                    // 单个获取、单个释放
                    //releaseSingle(semaphore, threadNum);
                    // 多个获取、多个释放 若获取的数量等于semaphore的最大数量 相当于单个执行
                    //releaseMore(semaphore, threadNum);
                    // 并发场景测试
                    releaseTryAcquire(semaphore, threadNum);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        log.info("Exec Finish!");
        // 关闭线程池
        exec.shutdown();
    }

    private static void test(int threadNum) {
        log.info("当前threadNum:{}", threadNum);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 单个释放
     *
     * @param semaphore
     * @param threadNum
     * @throws InterruptedException
     */
    private static void releaseSingle(Semaphore semaphore, int threadNum) throws InterruptedException {
        semaphore.acquire(); //获取一个许可
        test(threadNum);
        semaphore.release(); //释放一个许可
    }

    /**
     * 多个获取 多个释放
     * 相当于一个人直接占了所有坑位
     *
     * @param semaphore
     * @param threadNum
     * @throws InterruptedException
     */
    private static void releaseMore(Semaphore semaphore, int threadNum) throws InterruptedException {
        semaphore.acquire(3); //获取3个许可
        test(threadNum);
        semaphore.release(3); //释放3个许可
    }

    /**
     * 并发场景测试
     * <p>
     * semaphore.tryAcquire() // 为true表示，许可证-1
     * tryAcquire(int permits) // 为true表示，许可证数减去 permits
     * <p>
     * tryAcquire(long timeout, TimeUnit unit) // 为true表示，许可证减1 超时不再等待后续线程执行
     * tryAcquire(int permits, long timeout, TimeUnit unit) // 为true表示，许可证数减去 permits 超时不再等待后续线程执行
     *
     * </p>
     *
     * @param semaphore
     * @param threadNum
     */
    private static void releaseTryAcquire(Semaphore semaphore, int threadNum) throws InterruptedException {
        //尝试获取一个许可，也可以尝试获取多个许可，
        //支持尝试获取许可超时设置，超时后不再等待后续线程的执行
        if (semaphore.tryAcquire(5, TimeUnit.SECONDS)) {
            test(threadNum);
            semaphore.release(); //释放许可证
        }
    }

}
