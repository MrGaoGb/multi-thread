package com.mrgao.thread.condition;

/**
 * @author Mr.Gao
 * @apiNote:
 * @date 2025/3/31 15:50
 */
public class PrintABC_Wait_Notify {

    private static final Object lock = new Object();

    private static volatile int state = 0;

    private static final int cycleCount = 100;


    static class ThreadA extends Thread {

        @Override
        public void run() {
            for (int i = 0; i < cycleCount; ) {
                synchronized (lock) {
                    // 多线程并发，不能用if，必须用循环测试等待条件，避免虚假唤醒
                    while (state % 3 == 0) {
                        System.out.print("A");
                        state++;
                        i++;
                        // 唤醒
                        lock.notifyAll();
                    }
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.print(">>");
                }
            }
        }
    }

    static class ThreadB extends Thread {

        @Override
        public void run() {
            for (int i = 0; i < cycleCount; ) {
                synchronized (lock) {
                    while (state % 3 == 1) {
                        System.out.print("B");
                        state++;
                        i++;
                        lock.notifyAll();
                    }
                    try {
                        // 等待重新获取锁
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    static class ThreadC extends Thread {

        @Override
        public void run() {
            for (int i = 0; i < cycleCount; ) {
                synchronized (lock) {
                    while (state % 3 == 2) {
                        System.out.println("C");
                        state++;
                        i++;
                        lock.notifyAll();
                    }
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
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
