package com.mrgao.thread.condition;

/**
 * @author Mr.Gao
 * @apiNote:
 * @date 2025/3/31 15:42
 */
public class PrintABC_Synchronized {

    private static final Object lock = new Object();

    private static volatile int state = 0;

    static class ThreadA extends Thread {

        @Override
        public void run() {
            for (int i = 0; i < 10; ) {
                synchronized (lock) {
                    while (state % 3 == 0) {// 多线程并发，不能用if，必须用循环测试等待条件，避免虚假唤醒
                        System.out.print("A");
                        state++;
                        i++;
                    }
                    System.out.print(">>");
                }
            }
        }
    }

    static class ThreadB extends Thread {

        @Override
        public void run() {
            for (int i = 0; i < 10; ) {
                synchronized (lock) {
                    while (state % 3 == 1) {// 多线程并发，不能用if，必须用循环测试等待条件，避免虚假唤醒
                        System.out.print("B");
                        state++;
                        i++;
                    }
                }
            }
        }
    }

    static class ThreadC extends Thread {

        @Override
        public void run() {
            for (int i = 0; i < 10; ) {
                synchronized (lock) {
                    while (state % 3 == 2) {// 多线程并发，不能用if，必须用循环测试等待条件，避免虚假唤醒
                        System.out.println("C");
                        state++;
                        i++;
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
