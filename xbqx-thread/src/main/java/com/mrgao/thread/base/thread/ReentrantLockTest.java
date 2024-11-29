package com.mrgao.thread.base.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @Description TODO
 * @Author Mr.Gao
 * @Date 2024/10/22 0:13
 */
public class ReentrantLockTest {

    static ReentrantLock lock = new ReentrantLock();
    // 创建条件1
    static Condition c1 = lock.newCondition();
    // 创建条件2
    static Condition c2 = lock.newCondition();

    public static void main(String[] args) throws Exception {

        // 可中断
        interruptLock();

        // 可超时
        //timeout();

        // 多条件变量
        //multiCondition();
    }

    private static void interruptLock() {
        Thread t1 = new Thread(() -> {
            try {
                // 开启可中断的锁
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                System.out.println("等待的过程中被中断!");
                return;
            }

            try {
                System.out.println(Thread.currentThread().getName() + ": 获取到锁了!");
            } finally {
                lock.unlock();
            }
        }, "T1");

        // 主线程获取锁
        lock.lock();
        try {
            System.out.println("主线程获取了锁!");
            // 启动线程T1
            t1.start();

            //
            Thread.sleep(5000);

            // 中断线程t1
//            t1.interrupt();
//            System.out.println(Thread.currentThread().getName() + ":执行打断!");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }

    }

    private static void multiCondition() {
        new Thread(() -> {
            lock.lock();
            try {
                // 进入c1条件的等待
                c1.await();

                System.out.println(Thread.currentThread().getName() + ", 获取到锁!");

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }, "T1").start();

        new Thread(() -> {
            lock.lock();
            try {
                // 进入c2条件的等待
                c2.await();

                System.out.println(Thread.currentThread().getName() + ", 获取到锁!");

                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }

        }, "T2").start();

        new Thread(() -> {
            lock.lock();
            try {
                // 唤醒c1条件
                c1.signal();
                // 唤醒c2
                c2.signal();

                System.out.println(Thread.currentThread().getName() + ", 获取到锁!");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }, "T3").start();
    }

    /**
     * 可超时
     */
    public static void timeout() throws Exception {
        Thread t1 = new Thread(() -> {
            try {
                if (!lock.tryLock(2, TimeUnit.SECONDS)) {
                    System.out.println("t1 获取锁失败!");
                    return;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + ":获取到锁了!");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }, "t1");
        // 主线获取锁
        lock.lock();
        System.out.println("主线程获取到锁!");

        // 启动线程t1
        t1.start();
        try {
            Thread.sleep(3000);
        } finally {
            lock.unlock();
        }

    }
}
