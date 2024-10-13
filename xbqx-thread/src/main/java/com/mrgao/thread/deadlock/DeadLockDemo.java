package com.mrgao.thread.deadlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description 产生死锁
 * @Author Mr.Gao
 * @Date 2024/10/13 10:22
 */
public class DeadLockDemo {

    public static void main(String[] args) throws InterruptedException {

        // 产生死锁
        //deadLock();

        // 避免死锁
        // 1、不使用互斥锁，可以考虑使用原子类或乐观锁(CAS实现)
        // 2、破坏占有且等待条件
        //deadLock2();

        // 3、破坏不可抢占条件
        deadLock3();
    }

    /**
     * 产生死锁
     */
    private static void deadLock() {
        // lock锁1
        Object lock1 = new Object();
        // lock锁2
        Object lock2 = new Object();

        // 创建线程1
        Thread t1 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("Thread1 acquired lock");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2) {
                    System.out.println("Thread2 acquired lock");
                }
            }
        });

        // 创建线程2
        Thread t2 = new Thread(() -> {
            synchronized (lock2) {
                System.out.println("Thread2 acquired lock");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock1) {
                    System.out.println("Thread1 acquired lock");
                }
            }
        });

        // 开启线程
        t1.start();
        t2.start();
    }

    /**
     * 破坏占有且等待条件
     */
    private static void deadLock2() {
        // lock锁1
        Object lock1 = new Object();
        // lock锁2
        Object lock2 = new Object();

        Allocator allocator = Allocator.getInstance();

        // 创建线程1
        Thread t1 = new Thread(() -> {
            // TODO 一次性申请两把锁资源
            while (!allocator.allocate(lock1, lock2)) {
            }
            System.out.println("Thread1 acquired lock");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 释放锁资源
            allocator.release(lock1, lock2);
            System.out.println("Thread1 release lock");
        });

        // 创建线程2
        Thread t2 = new Thread(() -> {
            // 一次性申请两把锁资源
            while (!allocator.allocate(lock1, lock2)) {
            }
            System.out.println("Thread2 acquired lock");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 释放锁资源
            allocator.release(lock1, lock2);
            System.out.println("Thread2 release lock");
        });

        // 开启线程
        t1.start();
        t2.start();
    }

    /**
     * 破坏不可抢占条件:使用
     */
    private static void deadLock3() throws InterruptedException {
        Lock lock1 = new ReentrantLock();
        Lock lock2 = new ReentrantLock();

        // 创建线程1
        Thread t1 = new Thread(() -> {
            try {
                if (lock1.tryLock(5, TimeUnit.SECONDS)) {
                    try {
                        System.out.println(Thread.currentThread().getName() + ":@Thread1 acquired lock");
                        if (lock2.tryLock()) {
                            try {
                                System.out.println(Thread.currentThread().getName() + ":@Thread2 acquired lock");
                            } finally {
                                lock2.unlock();
                            }
                        }
                    } finally {
                        lock1.unlock();
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        // 创建线程2
        Thread t2 = new Thread(() -> {
            try {
                if (lock2.tryLock(5, TimeUnit.SECONDS)) {
                    try {
                        System.out.println(Thread.currentThread().getName() + ":#Thread2 acquired lock");
                        if (lock1.tryLock()) {
                            try {
                                System.out.println(Thread.currentThread().getName() + ":#Thread1 acquired lock");
                            } finally {
                                lock1.unlock();
                            }
                        }
                    } finally {
                        lock2.unlock();
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        try {
            // 主线程延迟5s
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t1.start();
        t2.start();
    }

}
