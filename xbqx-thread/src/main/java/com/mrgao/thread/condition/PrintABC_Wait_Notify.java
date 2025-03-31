package com.mrgao.thread.condition;

/**
 * @author Mr.Gao
 * @apiNote:
 * @date 2025/3/31 15:50
 */
public class PrintABC_Wait_Notify {

    private static final Object lock = new Object();

    private static volatile int state = 0;

    private static final int cycleCount = 10;


    static class ThreadA extends Thread {

        @Override
        public void run() {
            for (int i = 0; i < cycleCount; ) {
                synchronized (lock) {
                    // 多线程并发，不能用if，必须用循环测试等待条件，避免虚假唤醒
                    while (state % 3 == 0) {
                        System.out.print("A");
                        state++;
                        i++; //TODO 循环次数，当且仅当 输出一次才执行加一操作
                        // 唤醒
                        lock.notifyAll();
                    }
                    if (i < cycleCount) { // TODO 循环判断是否继续执行(如果不存在这一行代码，程序会一直运行)
                        try {
                            // 等待重新获取锁
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
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
                    if (i < cycleCount) {
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
                    if (i < cycleCount) {
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
    }

    public static void main(String[] args) {
        ThreadA threadA = new ThreadA();
        ThreadB threadB = new ThreadB();
        ThreadC threadC = new ThreadC();


        threadA.start();
        threadB.start();
        threadC.start();

//        while (true) {
//            try {
//                Thread.sleep(2 * 1000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            System.out.println("state:" + state);
//        }
    }
}
