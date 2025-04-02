package com.mrgao.thread.blockqueue.synchronousqueue;

import java.time.LocalDateTime;
import java.util.concurrent.SynchronousQueue;

/**
 * @Description 生产者
 * @Author Mr.Gao yn@20250402
 * @Date 2025/2/26 22:45
 */
public class Producer implements Runnable {

    private final SynchronousQueue<String> queue;

    public Producer(SynchronousQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
                String data = "Data " + i;
//                System.out.println("【Producing】: " + data);
                System.out.println("【Produced】-----------------" + i + "------------------" + LocalDateTime.now());
                queue.put(data);
                // 延时1s
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
