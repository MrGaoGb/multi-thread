package com.mrgao.thread.base.thread;

import com.mrgao.thread.utils.Print;

/**
 * @Description TODO
 * @Author Mr.Gao
 * @Date 2024/9/15 22:11
 */
public class SleepDemo {

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
                for (int i = 1; i < MAX_TURN; i++) {
                    Print.tco(getName() + ", 睡眠轮次：" + i);
                    // 线程睡眠一会
                    Thread.sleep(SLEEP_GAP);
                }
            } catch (InterruptedException e) {
                Print.tco(getName() + " 发生异常被中断.");
            }
            Print.tco(getName() + " 运行结束.");
        }
    }


    public static void main(String[] args) {
        Print.tco(Thread.currentThread().getName() + " 开始运行.");
        for (int i = 0; i < 5; i++) {
            SleepThread s = new SleepThread();
            s.start();
        }

        Print.tco(Thread.currentThread().getName() + " 运行结束.");
    }

}
