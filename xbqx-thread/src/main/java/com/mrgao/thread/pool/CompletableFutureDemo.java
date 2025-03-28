package com.mrgao.thread.pool;

import java.time.LocalDateTime;
import java.util.concurrent.*;


/**
 * @author Mr.Gao
 * @apiNote:CompletableFuture类中的线程池
 * @date 2025/3/23 10:22
 */
public class CompletableFutureDemo {

    static final int SMASK = 0xffff;
    static final int LIFO_QUEUE = 0;

    public static void main(String[] args) throws Exception {
        //verifyThreadPoolParams();
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        try {
            // 创建异步任务

            // 不带返回值
            //runAsync(executorService);

            // 带返回值
            //supplyAsync(executorService);

            // 结果处理
            thenRun(executorService);
            //thenRunAsync(executorService);
        } finally {
            executorService.shutdown();
        }
    }

    /**
     * thenRunAsync()：串行化任务处理,thenRunAsync使用的线程和supplyAsync使用的线程是两个线程
     *
     * @param executorService
     */
    private static void thenRunAsync(ExecutorService executorService) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        CompletableFuture.supplyAsync(() -> {
            processBizTime(2, "supplyAsync");
            int i = 1 / 0;
            return "666";
        }, executorService).exceptionally((e) -> {
            System.out.println("异常信息：" + e.getMessage());
            return "-1";
        }).thenRunAsync(() -> {
            processBizTime(5, "thenRunAsync");
            countDownLatch.countDown();
        });
        System.out.println("主线程执行其他业务逻辑");
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * thenRun()：串行化任务处理,thenRun使用的线程和supplyAsync使用的线程是同一个线程
     * <p>
     * 1、如果supplyAsync抛出异常，那么thenRun不会执行，会执行exceptionally
     * 2、如果thenRun抛出异常，那么supplyAsync和thenRun都会执行，supplyAsync会返回null，thenRun会抛出异常
     * </p>
     *
     * @param executorService
     */
    private static void thenRun(ExecutorService executorService) {
        CompletableFuture.supplyAsync(() -> {
            processBizTime(2, "supplyAsync");
            int i = 1 / 0;
            return "789";
        }, executorService)/*.exceptionally((e) -> {
            System.out.println(Thread.currentThread().getName() + ":异常信息：" + e.getMessage());
            return "-1";
        })*/.thenRun(() -> {
            processBizTime(6, "thenRun");
            //int i = 1 / 0;
        });
    }

    public static void verifyThreadPoolParams() {
        // CPU核数
        int parallelism = 2 - 1;
        int config = (parallelism & SMASK) | LIFO_QUEUE;

        int par = config & SMASK;
        System.out.println("parallelism:" + parallelism + ",config:" + config + ", par:" + par);
    }

    /**
     * 不返回结果（Runnable）
     *
     * @param executorService
     * @throws InterruptedException
     */
    private static void runAsync(ExecutorService executorService) throws InterruptedException {
        // 使用自定义线程池 executorService
        //CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
        //    processBizTime(5, "runAsync-自定义线程池策略");
        //}, executorService);

        // 使用默认线程池(由CPU核数决定的线程池，若CPU核数小于或等于2，那么底层采用的线程池为ThreadPerTaskExecutor,即就是每次new Thread().start)
        // 如果CPU核数大于2，那么底层采用的线程池为ForkJoinPool，ForkJoinPool默认的线程数是CPU核数-1
        CountDownLatch countDownLatch = new CountDownLatch(1);
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            processBizTime(2, "runAsync-默认线程池策略");
            countDownLatch.countDown();// 计数器减1
        });
        // --等待线程执行完毕
        countDownLatch.await();
    }

    /**
     * 返回结果（Supplier）
     *
     * @param executorService
     * @throws InterruptedException
     */
    private static void supplyAsync(ExecutorService executorService) {
        // 带返回值
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            // --模拟业务逻辑执行
            processBizTime(3, "supplyAsync");
            return 10;
        }, executorService);
        try {
            // 获取结果
            Integer resVal = completableFuture.get();
            System.out.println("resVal:" + resVal);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 执行业务逻辑
     */
    private static void processBizTime(long time, String runDesc) {
        System.out.println(Thread.currentThread().getName() + " >> " + runDesc + "(开始) , " + LocalDateTime.now());
        try {
            // 模拟耗时操作
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName() + " >> " + runDesc + "(结束), " + LocalDateTime.now());
    }
}
