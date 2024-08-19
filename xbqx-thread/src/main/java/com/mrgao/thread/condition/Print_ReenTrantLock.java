package com.mrgao.thread.condition;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Mr.Gao
 * @date 2024/8/19 17:11
 * @apiNote: <p>
 * 可重入锁和不可重入锁的区别在于：对线程多次获取锁的处理方式不同。
 * 可重入锁允许一个线程多次获取同一个锁；不可重入锁则不允许。
 * </p>
 */
public class Print_ReenTrantLock {

    private ReentrantLock lock = new ReentrantLock();

    public void testReentrantLock() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " get lock");
            long beginTime = System.currentTimeMillis();
            while (System.currentTimeMillis() - beginTime < 100) {

            }
            //线程再次获得该锁（可重入）
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " get lock again");
                long beginTime2 = System.currentTimeMillis();
                while (System.currentTimeMillis() - beginTime2 < 100) {

                }
            } finally {
                // 线程第一次释放锁
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + " release lock");
            }

        } finally {
            // 线程再次释放锁
            lock.unlock();
            System.out.println(Thread.currentThread().getName() + " release lock again");
        }
    }

    public static void main(String[] args) {
        final Print_ReenTrantLock test = new Print_ReenTrantLock();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                test.testReentrantLock();
            }
        }, "a");
        thread.start();
    }
}
