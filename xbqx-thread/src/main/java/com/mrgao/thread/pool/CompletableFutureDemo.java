package com.mrgao.thread.pool;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


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
            // 1、创建异步任务
            // 1.1 不带返回值
            //runAsync(executorService);
            // 1.2 带返回值
            //supplyAsync(executorService);

            // 2、结果处理
            // 2.1 thenRun同步执行
            //thenRun(executorService);
            // 2.2 thenRun异步执行
            //thenRunAsync(executorService);

            //thenAccept(executorService);
            //thenAcceptAsync(executorService);

            // 对结果进行处理
            //thenApply(executorService);
            //thenApplyAsync(executorService);

            // 组合多个Future
            //thenCompose(executorService);
            //thenComposeAsync(executorService);

            thenCombine(executorService);


        } finally {
            executorService.shutdown();
        }
    }

    private static void thenCombine(ExecutorService executorService) {
        // 两个独立的Future
        CompletableFuture<Integer> future5 = CompletableFuture.supplyAsync(() -> 10);
        CompletableFuture<Integer> future6 = CompletableFuture.supplyAsync(() -> 20);

        // 组合两个Future
        future5.thenCombine(future6, (a, b) -> {
            // processBizTime(3, "thenCombine");
            return a + b;
        }).thenAccept(s -> System.out.println("thenCombine >> 获取Val:" + s + ", " + LocalDateTime.now()));
    }

    private static void thenComposeAsync(ExecutorService executorService) throws Exception {
        CompletableFuture<String> future4 = CompletableFuture.supplyAsync(() -> {
                    processBizTime(2, "supplyAsync");
                    return "thenCompose";
                }, executorService)
                .thenComposeAsync(s -> {
                    System.out.println("thenCompose >> 获取Val:" + s);
                    processBizTime(4, "thenCompose");
                    return CompletableFuture.supplyAsync(() -> s + " World");
                });

        //String s = future4.get();
        //System.out.println(s);
    }

    private static void thenCompose(ExecutorService executorService) throws Exception {
        CompletableFuture<String> future4 = CompletableFuture.supplyAsync(() -> {
                    processBizTime(2, "supplyAsync");
                    return "thenCompose";
                }, executorService)
                .thenCompose(s -> {
                    System.out.println("thenCompose >> 获取Val:" + s);
                    processBizTime(2, "thenCompose");
                    return CompletableFuture.supplyAsync(() -> s + " World");
                });

        String s = future4.get();
        System.out.println(s);
    }

    private static void thenApplyAsync(ExecutorService executorService) throws Exception {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
                    processBizTime(2, "supplyAsync");
                    int i = 1 / 0;
                    return "666";
                }, executorService)
                /*.exceptionally((e) -> {
                    System.out.println("异常信息：" + e.getMessage());
                    return "-1";
                })*/
                .thenApplyAsync(res -> {
                    System.out.println("(B)thenApplyAsync >> 获取Val:" + res);
                    processBizTime(3, "thenApplyAsync");
                    System.out.println("(A)thenApplyAsync >> 获取Val:" + res);
                    // 将字符串转化为整数
                    return Integer.parseInt(res);
                }).thenApplyAsync(res -> {
                    // 将整数乘以2
                    System.out.println(Thread.currentThread().getName() + "-- thenApplyAsync!");
                    processBizTime(1, "thenApplyAsync");
                    return res * 2;
                });
        Integer val = completableFuture.get();
        System.out.println(val.getClass().getName() + ":" + val);
    }

    private static void thenApply(ExecutorService executorService) throws Exception {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
                    processBizTime(2, "supplyAsync");
                    int i = 1 / 0;
                    return "666";
                }, executorService)
                .exceptionally((e) -> {
                    System.out.println("异常信息：" + e.getMessage());
                    return "-1";
                })
                .thenApply(res -> {
                    System.out.println("(B)thenApply >> 获取Val:" + res);
                    processBizTime(3, "thenApply");
                    System.out.println("(A)thenApply >> 获取Val:" + res);
                    // 将字符串转化为整数
                    return Integer.parseInt(res);
                }).thenApply(res -> {
                    // 将整数乘以2
                    return res * 2;
                });
        Integer val = completableFuture.get();
        System.out.println(val.getClass().getName() + ":" + val);
    }

    private static void thenAcceptAsync(ExecutorService executorService) {
        CompletableFuture.supplyAsync(() -> {
                    processBizTime(2, "supplyAsync");
                    //int i = 1 / 0;
                    return "666";
                }, executorService)/*
                .exceptionally((e) -> {
                    System.out.println("异常信息：" + e.getMessage());
                    return "-1";
                })*/
                .thenAcceptAsync(res -> {
                    System.out.println("(B)thenAcceptAsync >> 获取Val:" + res);
                    processBizTime(3, "thenAcceptAsync");
                    System.out.println("(A)thenAcceptAsync >> 获取Val:" + res);
                });
    }

    private static void thenAccept(ExecutorService executorService) {
        CompletableFuture.supplyAsync(() -> {
                    processBizTime(2, "supplyAsync");
                    int i = 1 / 0;
                    return "666";
                }, executorService)
                .exceptionally((e) -> {
                    System.out.println("异常信息：" + e.getMessage());
                    return "-1";
                })
                .thenAccept(res -> {
                    processBizTime(3, "thenAccept");
                    System.out.println("thenAccept >> 获取Val:" + res);
                });
    }

    /**
     * thenRunAsync()：串行化任务处理,thenRunAsync使用的线程和supplyAsync使用的线程是两个线程
     *
     * @param executorService
     */
    private static void thenRunAsync(ExecutorService executorService) {
        CompletableFuture.supplyAsync(() -> {
            processBizTime(2, "supplyAsync");
            //int i = 1 / 0;
            return "666";
        }, executorService)/*.exceptionally((e) -> {
            System.out.println("异常信息：" + e.getMessage());
            return "-1";
        })*/.thenRunAsync(() -> {
            processBizTime(5, "thenRunAsync");
        });
    }

    /**
     * thenRun()：串行化任务处理,thenRun使用的线程和supplyAsync使用的线程是同一个线程
     *
     * @param executorService
     */
    private static void thenRun(ExecutorService executorService) {
        CompletableFuture.supplyAsync(() -> {
            processBizTime(2, "supplyAsync");
            //int i = 1 / 0;
            return "789";
        }, executorService).exceptionally((e) -> {
            System.out.println(Thread.currentThread().getName() + ":异常信息：" + e.getMessage());
            return "-1";
        }).thenRun(() -> {
            //int i = 1 / 0;
            processBizTime(6, "thenRun");
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
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            processBizTime(5, "runAsync-自定义线程池策略");
        }, executorService);

        // 使用默认线程池(由CPU核数决定的线程池，若CPU核数小于或等于2，那么底层采用的线程池为ThreadPerTaskExecutor,即就是每次new Thread().start)
        // 如果CPU核数大于2，那么底层采用的线程池为ForkJoinPool，ForkJoinPool默认的线程数是CPU核数-1
        //CountDownLatch countDownLatch = new CountDownLatch(1);
        //CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
        //    processBizTime(2, "runAsync-默认线程池策略");
        //    countDownLatch.countDown();// 计数器减1
        //});
        //// --等待线程执行完毕
        //countDownLatch.await();
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
            System.out.println(LocalDateTime.now() + ",>>> resVal:" + resVal);
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
