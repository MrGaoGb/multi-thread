package com.mrgao.java.base.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Mr.Gao
 * @apiNote:
 * @date 2025/4/14 17:03
 */
public class MyThreadPool {


    BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(5);

    /**
     * 核心线程数
     */
    int corePoolSize = 2;
    /**
     * 最大线程数
     */
    int maxPoolSize = 3;

    /**
     * 核心线程数
     */
    List<Thread> coreThreads = new ArrayList<>(corePoolSize);
    /**
     * 非核心线程数
     */
    List<Thread> extraThreads = new ArrayList<>();


    Runnable coreThreadRunnable = () -> {
        while (true) {
            try {
                Runnable take = queue.take();
                take.run();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    };

    Runnable extraThreadRunnable = () -> {
        while (true) {
            try {
                // 如果队列为空时，再存活10s之后，线程退出
                Runnable take = queue.poll(10, TimeUnit.SECONDS);
                if (take == null) break;
                take.run();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    };

    //List<Runnable> runnableList = new ArrayList<>();

    {
        //thread.start();
        System.out.println("线程池启动了...");
    }


    void shutdown() {
        coreThreads.forEach(thread -> thread.interrupt());
        extraThreads.forEach(thread -> thread.interrupt());
        System.out.println("线程池关闭了...");
    }

    /**
     * 提交任务处理的方法入口
     *
     * @param runnable
     */
    public void execute(Runnable runnable) {

        // 将任务添加至队列中
        //runnableList.add(runnable);

        if (coreThreads.size() < corePoolSize) {
            // 创建线程 添加至核心线程列表中
            Thread thread = new Thread(coreThreadRunnable);
            coreThreads.add(thread);
            // 启动线程
            thread.start();
            return;
        }

        if (queue.offer(runnable)) {
            System.out.println("队列未满，将任务添加至阻塞队列中...");
            return;
        }

        if (!queue.offer(runnable) && extraThreads.size() < (maxPoolSize - corePoolSize)) {
            System.out.println("队列已满，将任务添加至非核心线程列表中");
            Thread thread = new Thread(extraThreadRunnable);
            extraThreads.add(thread);
            // 启动线程
            thread.start();
            return;
        }

        if (!queue.offer(runnable)) {
            System.out.println("队列已满 && 非核心线程数也满了 则表示是真的满了,不能再添加了...!");
            throw new IllegalStateException("任务被拒绝了...");
        }
    }
}
