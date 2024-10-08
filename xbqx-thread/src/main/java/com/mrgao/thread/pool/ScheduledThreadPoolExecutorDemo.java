package com.mrgao.thread.pool;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ScheduledThreadPoolExecutor测试
 */
public class ScheduledThreadPoolExecutorDemo {
    public static void main(String[] args) throws Exception {

        // 创建线程
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);

        AtomicInteger num = new AtomicInteger(0);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            try {
                if (num.get() >= 5) {
                    int n = 10 / 0; // 此处不会抛出异常
                }
                System.out.println("ScheduledThreadPoolExecutorDemo....,开始运行了！" + LocalDateTime.now());
                num.incrementAndGet();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("该线程执行异常了，异常原因:" + e.getMessage());
                throw e;
            }
        }, 10, 2, TimeUnit.SECONDS);

        // 新建一个线程
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            System.out.println("另外一个延时线程,开始运行了! " + LocalDateTime.now());
        }, 1000, 1000, TimeUnit.MILLISECONDS);

//        scheduledExecutorService.scheduleWithFixedDelay()

        //主线程休眠30秒
        Thread.sleep(60000);
        System.out.println("正在关闭线程池...");
        // 关闭线程池
        scheduledExecutorService.shutdown();
        boolean isClosed;
        // 等待线程池终止
        do {
            isClosed = scheduledExecutorService.awaitTermination(1, TimeUnit.DAYS);
            System.out.println("正在等待线程池中的任务执行完成");
        } while (!isClosed);
        System.out.println("所有线程执行结束，线程池关闭");


    }
}
