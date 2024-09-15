package com.mrgao.thread.pool;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Mr.Gao
 * @apiNote:
 * @date 2024/9/4 13:53
 */
public class ThreadPoolExecutorDemo {
    public static void main(String[] args) {
        //// 创建单个线程池 标识核心线程数为1;最大工作线程数为1;阻塞队列大小为Integer.MAX_VALUE
        //Executors.newSingleThreadExecutor();
        //
        //// 创建固定线程数 自定义核心线程数和最大线程数为:nThreads,阻塞对象队列大小为Integer.MAX_VALUE
        //Executors.newFixedThreadPool(5);
        //
        //// 创建定时线程池，可以按照指定的频率去执行，可以定时的实现动态线程池的检测功能
        //Executors.newScheduledThreadPool(5);
        //
        //// 即就是 ForkJoinPool 可用于任务计算 可以通过任务拆分的形式并行执行
        //Executors.newWorkStealingPool();
        //
        //System.out.println(Runtime.getRuntime().availableProcessors());

        /**
         * 自定义创建线程池
         * 1、工作队列大小指定为200
         * 2、使用默认的线程池工厂
         * 3、拒绝策略使用AbortPolicy，直接抛出异常
         */
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                10,
                20,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(200),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        ) {
            // TODO 线程执行完毕后的钩子函数
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
                // 可以做一些自己想做的事情，例如：调用threadLocal的remove方法
                // 清空TheadLocal实例的本地值
                // threadLocal.remove();
            }
        };


    }
}
