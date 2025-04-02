package com.mrgao.thread.blockqueue.synchronousqueue;

import java.time.LocalDateTime;
import java.util.concurrent.SynchronousQueue;

/**
 * @Description 消费者线程
 * @Author Mr.Gao
 * @Date 2025/2/26 22:47
 */
public class Consumer implements Runnable {

    private final SynchronousQueue<String> queue;

    public Consumer(SynchronousQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
//                System.out.println("【Consuming】: " + i);
                Thread.sleep(5000);
                String data = queue.take();
                System.out.println("【Consumed】: " + data + "-----------" + LocalDateTime.now());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
