package com.mrgao.java.base.scheduled;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.LockSupport;

/**
 * @Description 手撕循环定时任务
 * @Author Mr.Gao
 * @Date 2025/4/13 17:21
 */
public class MyScheduledService {

    /**
     * 定义一个可用的线程池
     */
    private static ExecutorService executorService = Executors.newFixedThreadPool(5);

    /**
     * 定义一个触发器
     */
    private Trigger trigger = new Trigger();

    /**
     * 提交一个延时的定时任务
     *
     * @param task
     * @param delay
     */
    public void scheduledTask(Runnable task, long delay) {
        // 将任务提交至线程池中
        Job job = new Job();
        job.setTask(task);
        job.setDelay(delay);
        job.setStartTime(System.currentTimeMillis() + delay);
        trigger.queue.offer(job);
        // 触发唤醒
        trigger.wakeUp();
    }

    /**
     * 触发器：用来延时处理提交的任务
     */
    class Trigger {

        // 需要一个容器来装载提交的任务，但是又因为需要根据任务的优先级来处理提交的任务，所以需要一个优先级阻塞队列来装载任务
        PriorityBlockingQueue<Job> queue = new PriorityBlockingQueue<>();

        // 由于提交的任务不能让主线程阻塞，所以触发器中需要一个异步线程来处理提交的任务

        Thread thread = new Thread(() -> {

            while (true) {
                //
                while (queue.isEmpty()) {
                    // 如果队列为空时，为避免一直耗时CPU资源，所以此处用park阻塞当前线程
                    LockSupport.park();
                }

                // --表示队列中有任务，需要处理
                // 从队列中获取最近需要执行的任务（peek不会移除元素,而 poll会移除元素）
                Job laterJob = queue.peek();
                if (laterJob.getStartTime() < System.currentTimeMillis()) {
                    // 表示当前有需要执行的任务，则从队列中移除最新任务
                    laterJob = queue.poll();// 此处表示获取最新需要执行的任务

                    // 将该任务提交至线程池中
                    executorService.execute(laterJob.getTask());
                    // 若需要实现循环任务，则需要将该任务再次提交至队列中
                    Job newJob = new Job();
                    newJob.setTask(laterJob.getTask());
                    newJob.setDelay(laterJob.getDelay());
                    newJob.setStartTime(System.currentTimeMillis() + laterJob.getDelay());
                    queue.offer(newJob);
                } else {
                    // 表示当前没有需要执行的任务，则阻塞当前线程
                    LockSupport.parkUntil(laterJob.getStartTime());
                }
            }

        });

        {
            thread.start();
            System.out.println("触发器启动了...!");
        }


        public void wakeUp() {
            LockSupport.unpark(thread);
        }

    }

}
