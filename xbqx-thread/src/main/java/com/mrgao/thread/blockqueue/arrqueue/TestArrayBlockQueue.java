package com.mrgao.thread.blockqueue.arrqueue;

import java.time.LocalDateTime;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Mr.Gao
 * @apiNote:阻塞队列之数组队列
 * @date 2025/4/9 11:41
 */
public class TestArrayBlockQueue {
    /**
     * 循环次数
     */
    private static final int CYCLE_COUNT = 10;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        try {
            // 创建一个容量为3的数组阻塞队列
            BlockingQueue<String> queue = new ArrayBlockingQueue<>(3);

            // 创建一个生产者
            Producer producer = new Producer(queue, CYCLE_COUNT);
            // 创建两个消费者
            Consumer consumer1 = new Consumer(queue, CYCLE_COUNT);
            //Consumer consumer2 = new Consumer(queue, CYCLE_COUNT);

            // 将任务提交至线程池中
            executorService.execute(producer);
            executorService.execute(consumer1);
            //executorService.execute(consumer2);
        } finally {
            executorService.shutdown();
        }
    }


    /**
     * 生产者
     */
    public static class Producer implements Runnable {

        private final BlockingQueue<String> queue;
        private final int cycle;

        public Producer(BlockingQueue<String> queue, int cycle) {
            this.queue = queue;
            this.cycle = cycle;
        }

        @Override
        public void run() {
            for (int i = 0; i < cycle; i++) {
                try {
                    // 每一秒插入一个产品
                    Thread.sleep(1000);
                    // 模拟生产一个产品
                    String product = "P" + i;
                    queue.put(product);
                    System.out.println("【生产者】生产时间:" + LocalDateTime.now() + ",生产一个商品:" + product);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class Consumer implements Runnable {

        private final BlockingQueue<String> queue;

        private final int cycle;

        public Consumer(BlockingQueue<String> queue, int cycle) {
            this.queue = queue;
            this.cycle = cycle;
        }

        @Override
        public void run() {
            for (int i = 0; i < cycle; i++) {
                try {
                    // 每一秒消费一个产品
                    Thread.sleep(5000);
                    // 模拟消费一个产品
                    String take = queue.poll();
                    System.out.println("【消费者】消费时间:" + LocalDateTime.now() + ", 消费一个商品:" + take);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
