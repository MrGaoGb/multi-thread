package com.mrgao.java.base.threadpool;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.mrgao.java.base.threadpool.reject.ThrowExceptionRejectedHandler;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * @author Mr.Gao
 * @apiNote:
 * @date 2025/4/14 17:03
 */
public class MyThreadPool {

    /**
     * 阻塞队列
     */
    private BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(5);

    /**
     * 定义一个锁对象
     */
    private ReentrantLock lock = new ReentrantLock();

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

    private volatile AtomicInteger coreThreadCount = new AtomicInteger(0);
    private volatile AtomicInteger extraThreadCount = new AtomicInteger(0);

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

    /**
     * 打印线程池相关参数信息
     */
    Thread printThread = new Thread(() -> {
        while (true) {
            try {
                // 每秒打印一次
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // 移除非核心线程数
            List<ExtraThread> waitThreadList = extraThreads.stream().filter(thread -> thread instanceof ExtraThread)
                    .map(thread -> (ExtraThread) thread)
                    .filter(extraThread -> !extraThread.isFlag())
                    .collect(Collectors.toList());
            if (CollUtil.isNotEmpty(waitThreadList)) {
                waitThreadList.forEach(extraThread -> {
                    boolean remove = extraThreads.remove(extraThread);
                    System.out.println("移除线程:" + extraThread.getName() + "，移除" + (remove ? "成功" : "失败"));
                });
            }
            System.out.println("打印线程信息，核心线程:" + coreThreads.size() + ", 非核心线程数:" + extraThreads.size() + ", 队列中任务数:" + queue.size() + ", 非核心线程:" + waitThreadList.size());
        }
    });

    @Override
    public String toString() {
        return "MyThreadPool{" + "queue=" + queue + ", corePoolSize=" + corePoolSize + ", maxPoolSize=" + maxPoolSize + ", keepAliveTime=" + keepAliveTime + ", timeUnit=" + timeUnit + ", rejectHandler=" + rejectHandler + '}';
    }


    {
        //thread.start();
        // 启动打印线程任务
        printThread.start();
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
                Thread coreThread = new CoreThread(runnable);
                coreThreads.add(coreThread);
                coreThread.setName("core-" + coreThreadCount.getAndIncrement());
                System.out.println("创建核心线程处理任务" + coreThread.getName());
                // 启动线程
                coreThread.start();
                return;
            }

            // 添加至阻塞队列中
            if (queue.offer(runnable)) {
                System.out.println("队列未满，将任务添加至阻塞队列中...");
                return;
            }

            //
            if (extraThreads.size() < (maxPoolSize - corePoolSize)) {
                System.out.println("队列已满，将任务添加至非核心线程列表中");
                Thread extraThread = new ExtraThread(runnable);
                extraThreads.add(extraThread);
                extraThread.setName("extra-" + extraThreadCount.getAndIncrement());
                // 启动非核心线程
                extraThread.start();
                return;
            }

            if (!queue.offer(runnable)) {
                System.err.println("队列已满 && 非核心线程数也满了 则表示是真的满了,不能再添加了...!");
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

        private Runnable task;

        CoreThread(Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {
            Runnable runTask = null;
            while (true) {
                try {
                    // 任务不为null时，则优先执行传入的task
                    runTask = ObjectUtil.isNotNull(task) ? task : queue.take();
                    runTask.run();

                    // 当前任务执行完毕后，将当前任务置为null，方便获取下一个任务
                    task = null;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }

    /**
     * 非核心线程处理逻辑
     */
    @Getter
    @Setter
    class ExtraThread extends Thread {

        private boolean flag = true;

        private Runnable task;

        ExtraThread(Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {
            Runnable runTask = null;
            while (true) {
                try {
                    // 如果队列为空时，再存活10s之后，线程退出
                    runTask = ObjectUtil.isNotNull(task) ? task : queue.poll(keepAliveTime, timeUnit);
                    if (runTask == null) break;

                    // 执行目标任务
                    runTask.run();

                    // 当前任务执行完毕后，将当前任务置为null，方便获取下一个任务
                    task = null;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            // 将标志位设置为false，表示线程已经退出了
            flag = false;
            System.out.println("非核心线程:" + Thread.currentThread().getName() + " 退出了...");
        }
    }
}
