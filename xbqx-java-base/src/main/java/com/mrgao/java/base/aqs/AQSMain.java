package com.mrgao.java.base.aqs;

import java.util.concurrent.CountDownLatch;

/**
 * @Description AQS运行示例
 * @Author Mr.Gao
 * @Date 2025/4/13 19:17
 */
public class AQSMain {


    static int countDownNum = 10;

    public static void main(String[] args) throws InterruptedException {
        int[] count = new int[]{0};
        // 创建锁对象 使用ReentrantLock
//        Lock lock = new ReentrantLock();
        // 创建锁对象 使用自定义Lock
//        MyLock lock = new MyLock();
        ReenMyLock lock = new ReenMyLock();// 支持可重入锁

        CountDownLatch countDownLatch = new CountDownLatch(countDownNum);

        for (int i = 0; i < countDownNum; i++) {
            new Thread(() -> {
                // 加锁
                for (int j = 0; j < 10; j++) {
                    lock.lock();
                    sleep(2);
                    count[0]++;
                }

                // 解锁
                for (int j = 0; j < 10; j++) {
                    lock.unlock();
                }
                countDownLatch.countDown();
            }, "thread-" + i).start();
        }

        // 主线程阻塞
        countDownLatch.await();

        // 所有线程执行完毕后 得到累加后的值
        System.out.println("得到累加后的值:" + count[0]);
    }

    public static void sleep(long timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
