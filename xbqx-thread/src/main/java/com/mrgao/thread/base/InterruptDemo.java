package com.mrgao.thread.base;

import com.mrgao.thread.utils.Print;

/**
 * @Description Interrupt：Thread.interrupt()方法只是改变中断状态，不会中断一个正在运行的线程，线程是否停止执行，需要用户程序去监视线程的
 * isInterrupted()状态，并进行相应的处理。
 * @Author Mr.Gao
 * @Date 2024/9/15 22:11
 */
public class InterruptDemo {

    public static final int SLEEP_GAP = 5000; //睡眠时长5秒
    public static final int MAX_TURN = 50; //睡眠次数，稍微多点方便使用Jstack

    static class SleepThread extends Thread {

        static int threadSeqNumber = 1;

        public SleepThread() {
            super("sleepThread-" + threadSeqNumber);
            threadSeqNumber++;
        }

        @Override
        public void run() {
            try {
                Print.tco(getName() + ", 进入睡眠..");
                // 线程睡眠一会
                Thread.sleep(SLEEP_GAP);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Print.tco(getName() + " 发生异常被中断.");
                return;
            }
            Print.tco(getName() + " 运行结束.");
        }
    }


    public static void main(String[] args) throws Exception {
        System.out.println("程序开始运行...!");
        // 启动线程1
        SleepThread sleepThread = new SleepThread();
        sleepThread.start();
        // 启动线程2
        SleepThread sleepThread2 = new SleepThread();
        sleepThread2.start();

        // 主线程等待2s
        Thread.sleep(2000);

        // 线程1中断
        sleepThread.interrupt();

        // 主线程等待5s
        Thread.sleep(5000);
        // 线程2中断 此时线程2已经终止
        sleepThread2.interrupt();

        // 主线程等待1s
        Thread.sleep(1000);
        System.out.println("程序运行结束~~!");
    }

}
