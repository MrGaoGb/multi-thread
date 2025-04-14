package com.mrgao.java.base.threadpool;

import com.mrgao.java.base.threadpool.reject.ThrowExceptionRejectedHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Mr.Gao
 * @apiNote:
 * @date 2025/4/14 17:03
 */
public class MyThreadPool {

    /**
     * 阻塞队列
     */
    BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(5);

    /**
     * 定义一个锁对象
     */
    ReentrantLock lock = new ReentrantLock();

    /**
     * 核心线程数
     */
    private volatile int corePoolSize = 2;
    /**
     * 最大线程数
     */
    private volatile int maxPoolSize = 3;
    /**
     * 非核心线程数存活时间
     */
    private volatile long keepAliveTime = 10;
    /**
     * 时间单位
     */
    private volatile TimeUnit timeUnit = TimeUnit.SECONDS;

    /**
     * 拒绝策略 默认抛出异常
     */
    IRejectHandler rejectHandler = new ThrowExceptionRejectedHandler();

    /**
     * 构造器
     *
     * @param corePoolSize
     * @param maxPoolSize
     * @param keepAliveTime
     * @param timeUnit
     * @param queue
     */
    public MyThreadPool(int corePoolSize, int maxPoolSize, long keepAliveTime, TimeUnit timeUnit, BlockingQueue<Runnable> queue) {
        this.queue = queue;
        this.corePoolSize = corePoolSize;
        this.maxPoolSize = maxPoolSize;
        this.keepAliveTime = keepAliveTime;
        this.timeUnit = timeUnit;
    }


    /**
     * 定义构造器
     *
     * @param corePoolSize
     * @param maxPoolSize
     * @param keepAliveTime
     * @param timeUnit
     * @param queue
     * @param rejectHandler
     */
    public MyThreadPool(int corePoolSize, int maxPoolSize, long keepAliveTime, TimeUnit timeUnit, BlockingQueue<Runnable> queue, IRejectHandler rejectHandler) {
        this.queue = queue;
        this.corePoolSize = corePoolSize;
        this.maxPoolSize = maxPoolSize;
        this.keepAliveTime = keepAliveTime;
        this.timeUnit = timeUnit;
        this.rejectHandler = rejectHandler;
    }

    /**
     * 核心线程数
     */
    List<Thread> coreThreads = new ArrayList<>(corePoolSize);
    /**
     * 非核心线程数
     */
    List<Thread> extraThreads = new ArrayList<>();

//    Thread printThread = new Thread(() -> {
//        while (true) {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            System.out.println("打印线程信息，核心线程:" + coreThreads.size() + ", 非核心线程数:" + extraThreads.size() + ", 队列中任务数:" + queue.size());
//        }
//    });

    @Override
    public String toString() {
        return "MyThreadPool{" + "queue=" + queue + ", corePoolSize=" + corePoolSize + ", maxPoolSize=" + maxPoolSize + ", keepAliveTime=" + keepAliveTime + ", timeUnit=" + timeUnit + ", rejectHandler=" + rejectHandler + '}';
    }


    {
        //thread.start();
        System.out.println("线程池启动了...");
    }


    void shutdown() {
//        coreThreads.forEach(thread -> thread.interrupt());
//        extraThreads.forEach(thread -> thread.interrupt());
//        System.out.println("线程池关闭了...");
    }

    /**
     * 提交任务处理的方法入口
     *
     * @param runnable
     */
    public void execute(Runnable runnable) {
        lock.lock();
        try {
            if (coreThreads.size() < corePoolSize) {
                // 创建线程 添加至核心线程列表中
                Thread coreThread = new CoreThread();
                coreThreads.add(coreThread);
                coreThread.setName("core");
                System.out.println("创建核心线程处理任务" + coreThread.getName());
                // 启动线程
                coreThread.start();
            }

            // 添加至阻塞队列中
            if (queue.offer(runnable)) {
                System.out.println("队列未满，将任务添加至阻塞队列中...");
                return;
            }

            //
            if (extraThreads.size() < (maxPoolSize - corePoolSize)) {
                System.out.println("队列已满，将任务添加至非核心线程列表中");
                Thread extraThread = new ExtraThread();
                extraThreads.add(extraThread);
                extraThread.setName("extra");
                // 启动非核心线程
                extraThread.start();
            }

            if (!queue.offer(runnable)) {
                System.out.println("队列已满 && 非核心线程数也满了 则表示是真的满了,不能再添加了...!");
                rejectHandler.reject(runnable, this);
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * 核心线程处理逻辑
     */
    class CoreThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Runnable take = queue.take();
                    take.run();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }

    /**
     * 非核心线程处理逻辑
     */
    class ExtraThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    // 如果队列为空时，再存活10s之后，线程退出
                    Runnable take = queue.poll(keepAliveTime, timeUnit);
                    if (take == null) break;
                    take.run();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("非核心线程:" + Thread.currentThread().getName() + " 退出了...");
        }
    }
}
