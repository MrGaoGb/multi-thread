package com.mrgao.thread.pool;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Mr.Gao
 * @date 2024/8/23 15:56
 * @apiNote:
 */
public class ThreadStopDemo {

    private static ReentrantLock lock = new ReentrantLock();


    public static void main(String[] args) {

        // stop方法
        threadStop();

    }

    /**
     * 验证：Thread的stop方法是否会触发finally中的lock的unlock方法
     * <p>
     * stop方法已经被官方废弃了
     * </p>
     */
    public static void threadStop() {
        lock.lock();
        try {
            Thread thread = new Thread(() -> System.out.println("Stop线程启动了!"));
            thread.start();
            try {
                // 模拟执行业务逻辑
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("业务逻辑执行完毕,继续向下执行!");
            // 调用stop方法
            thread.stop();
        } finally {
            lock.unlock();
            System.out.println("释放锁成功...");
        }
    }
}
