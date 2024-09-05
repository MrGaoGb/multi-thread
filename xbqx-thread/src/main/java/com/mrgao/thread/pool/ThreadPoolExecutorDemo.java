package com.mrgao.thread.pool;

import java.util.concurrent.Executors;

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


    }
}
