package com.mrgao.thread.pool;

import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Mr.Gao
 * @apiNote: 验证ThreadPoolExecutor
 * @date 2024/10/18 23:55
 */
public class TestThreadPoolExecutor {

    /**
     * 创建一个静态内部类,实现自定义Task任务
     */
    static class ThreadTask implements Runnable {

        private final String name;
        private final long duration;

        public ThreadTask(String name, long duration) {
            this.name = name;
            this.duration = duration;
        }

        @SneakyThrows
        @Override
        public void run() {
            String logBeginInf = String.format("thread:[%s]- ThreadName:%s >> %s!", Thread.currentThread().getName(), this, "running");
            System.out.println(logBeginInf);
            TimeUnit.SECONDS.sleep(duration);
            String logEndInf = String.format("thread:[%s]- ThreadName:%s >> %s!", Thread.currentThread().getName(), this, "end");
            System.out.println(logEndInf);
        }

        @Override
        public String toString() {
            return "ThreadTask(" + name + ")";
        }
    }

    public static void main(String[] args) {
        AtomicInteger c = new AtomicInteger(0);
        // 创建自定义线程池
        /**
         * <p>线程池的7大参数</p>
         * corePoolSize: 核心线程数
         * maximumPoolSize：最大线程数( 最大线程数 = 核心线程数 + 非核心(临时)线程数)
         * keepAliveTime：非核心(临时)线程数 在不工作时存活时间
         * unit：存活时间单位
         * workQueue：阻塞队列大小
         * threadFactory：线程工厂，用于创建线程
         * handler：四种拒绝策略
         *     - AbortPolicy：直接抛出异常RejectedExecutionException
         *     - CallerRunsPolicy: 由当前线程或主线程执行当前任务
         *     - DiscardOldestPolicy: 丢弃queue中最早的任务
         *     - DiscardPolicy: 什么也不做
         */
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2,
                3,
                10,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(2),
                r -> new Thread(r, "myThread" + c.incrementAndGet()),
                new ThreadPoolExecutor.DiscardPolicy()
        );
        // -- 输出线程池参数信息
        logThreadInf(threadPoolExecutor);

        threadPoolExecutor.submit(new ThreadTask("1", 1000));
        logThreadInf(threadPoolExecutor);

        threadPoolExecutor.submit(new ThreadTask("2", 1000));
        logThreadInf(threadPoolExecutor);

        threadPoolExecutor.submit(new ThreadTask("3", 3));
        logThreadInf(threadPoolExecutor);

        threadPoolExecutor.submit(new ThreadTask("4", 4));
        logThreadInf(threadPoolExecutor);

        threadPoolExecutor.submit(new ThreadTask("5", 5));
        logThreadInf(threadPoolExecutor);

        threadPoolExecutor.submit(new ThreadTask("6", 6));
        logThreadInf(threadPoolExecutor);

        // 关闭线程池
        threadPoolExecutor.shutdown();
    }

    /**
     * 输出线程池大小情况
     */
    @SneakyThrows
    private static void logThreadInf(ThreadPoolExecutor pool) {
        long taskCount = pool.getTaskCount();
        // 返回池中的当前线程数
        int poolSize = pool.getPoolSize();
        int activeCount = pool.getActiveCount();
        BlockingQueue<Runnable> queue = pool.getQueue();
        List<Object> tasks = new ArrayList<>();
        for (Runnable runnable : queue) {
            Field callableField = FutureTask.class.getDeclaredField("callable");
            callableField.setAccessible(true);
            Object adapter = callableField.get(runnable);
            Class<?> aClass = Class.forName("java.util.concurrent.Executors$RunnableAdapter");
            Field taskField = aClass.getDeclaredField("task");
            taskField.setAccessible(true);
            Object task = taskField.get(adapter);
            tasks.add(task);
        }
        // 日志输出信息
        String logInfo = String.format("【线程池参数信息】thread:[%s] poolSize:%d, activeCount:%d, taskCount:%d, queue:%s",
                Thread.currentThread().getName(),
                poolSize, activeCount, taskCount, tasks);
        System.out.println(logInfo);
    }

}
