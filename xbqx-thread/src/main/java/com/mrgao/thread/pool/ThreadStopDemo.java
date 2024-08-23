package com.mrgao.thread.pool;

import java.util.concurrent.TimeUnit;
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
//        stopThread();

        // interrupt方法
        interruptThread();

    }

    /**
     * 验证：Thread的stop方法是否会触发finally中的lock的unlock方法
     * <p>
     * stop方法已经被官方废弃了
     * 备注：stop方法会杀死线程，表明在调用完stop方法后，没有再继续执行
     * </p>
     * <p>
     * 输出结果：
     * Stop线程启动了!
     * Stop线程业务执行中...!
     * main线程 业务逻辑执行完毕,继续向下执行!
     * 调用完stop方法后...!
     * 释放锁成功...
     * </p>
     */
    private static void stopThread() {
        lock.lock();
        try {
            Thread thread = new Thread(() -> {
                System.out.println("Stop线程启动了!");
                try {
                    // 模拟线程业务执行逻辑
                    System.out.println("Stop线程业务执行中...!");
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("stop线程执行完毕!");

            });
            thread.start();
            try {
                // 当前线程 模拟执行业务逻辑
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("main线程 业务逻辑执行完毕,继续向下执行!");
            // 调用stop方法
            thread.stop();
            System.out.println("调用完stop方法后...!");
        } finally {
            lock.unlock();
            System.out.println("释放锁成功...");
        }
    }

    /**
     * interrupt方法中断线程
     * <p>
     * interrupt方法仅仅是通知线程，线程有机会执行一些后续操作，同时也可以无视这个通知。被interrupt的线程，有两种方式接受异常通知：
     * 一种是异常，另一种是主动检测。
     * </p>
     * <p>
     * 【通过异常接受通知】：
     * 当线程A处于WAITING、TIMED_WAITING状态时，如果其他线程调用线程A的interrupt方法，则会使线程A返回RUNNABLE状态，同时
     * 线程A的代码会触发InterruptedException异常，线程转换到WAITING、TIME_WAITING状态的触发条件，都是调用了类似wait()
     * join()、sleep()方法，都会throws InterruptedException这个异常，这个异常触发的条件是其他线程调用了interrupt()方法。
     * </p>
     * <p>
     * 【主动检测】：
     * 如果线程处于RUNNABLE状态，并且没有阻塞在某个IO操作上，例如中断计算基因组序列的线程A，此时就得依赖线程A主动检查中断状态了，
     * 如果其他线程调用了线程A的interrupt方法，那么线程A可以通过isInterrupt()方法，来检测自己是不是被中断了。
     * </p>
     */
    private static void interruptThread() {
        lock.lock();
        try {
            Thread thread = new Thread(() -> {
                System.out.println("interrupt线程启动了!");
                try {
                    // 模拟线程业务执行逻辑
                    System.out.println("interrupt线程业务执行中...!");
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("interrupt线程执行完毕!");

            });
            thread.start();
//            try {
//                // 当前线程 模拟执行业务逻辑
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
            System.out.println("main线程 业务逻辑执行完毕,继续向下执行!");
            System.out.println("子线程是否被中断了: " + thread.isInterrupted());
            // 调用interrupt方法
            thread.interrupt();
            while (true) {
                if (thread.isInterrupted())
                    break;
            }
            System.out.println("子线程是否被中断了: " + thread.isInterrupted());
            System.out.println("调用完interrupt方法后...!");
        } finally {
            lock.unlock();
            System.out.println("释放锁成功...");
        }
    }
}
