package com.mrgao.thread.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Mr.Gao
 * @date 2024/8/19 17:29
 * @apiNote:
 */
public class PrintABC_Condition {

    private static ReentrantLock lock = new ReentrantLock();
    private static Condition A = lock.newCondition();
    private static Condition B = lock.newCondition();
    private static Condition C = lock.newCondition();
    private volatile static int state = 0;

    static class ThreadA extends Thread {

        @Override
        public void run() {
            for (int i = 0; i < 10; ) {
                lock.lock();
                try {
                    while (state % 3 != 0) {// 多线程并发，不能用if，必须用循环测试等待条件，避免虚假唤醒
                        A.await();// a释放lock锁
                    }
                    System.out.print("A");
                    state++;
                    i++;
                    // 唤醒B
                    B.signal();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();// unlock()操作必须放在finally块中
                }
            }
        }
    }

    static class ThreadB extends Thread {

        @Override
        public void run() {
            for (int i = 0; i < 10; ) {
                lock.lock();
                try {
                    while (state % 3 != 1) {// 多线程并发，不能用if，必须用循环测试等待条件，避免虚假唤醒
                        B.await();
                    }
                    System.out.print("B");
                    state++;
                    i++;
                    C.signal();// 唤醒C
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();// unlock()操作必须放在finally块中
                }
            }
        }
    }

    static class ThreadC extends Thread {

        @Override
        public void run() {
            for (int i = 0; i < 10; ) {
                lock.lock();
                try {
                    while (state % 3 != 2) {// 多线程并发，不能用if，必须用循环测试等待条件，避免虚假唤醒
                        C.await();
                    }
                    System.out.println("C");
                    state++;
                    i++;
                    // 唤醒A
                    A.signal();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();// unlock()操作必须放在finally块中
                }
            }
        }
    }

    public static void main(String[] args) {
        new ThreadA().start();
        new ThreadB().start();
        new ThreadC().start();
    }
}
