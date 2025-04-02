package com.mrgao.thread.blockqueue.synchronousqueue;

import java.util.concurrent.SynchronousQueue;

/**
 * @Description 主程序执行
 * @Author Mr.Gao
 * @Date 2025/2/26 22:48
 */
public class MainSynchronousQueueExecute {

    public static void main(String[] args) {

        SynchronousQueue<String> queue = new SynchronousQueue<>();

        // 生产者线程
        Thread producerThread = new Thread(new Producer(queue));
        // 消费者线程
        Thread consumerThread = new Thread(new Consumer(queue));

        // 启动线程
        producerThread.start();
        consumerThread.start();
    }
}
