package com.mrgao.thread.base.thread;

import com.mrgao.thread.utils.Print;

import java.time.LocalDateTime;

/**
 * @Description join方法
 * @Author Mr.Gao
 * @Date 2024/9/15 23:27
 */
public class JoinDemo {

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


    public static void main(String[] args) {
        // 创建线程1
        SleepThread sleepThread = new SleepThread();
        System.out.println("线程1启动..." + LocalDateTime.now());
        sleepThread.start();
        try {
            // 合并线程1 不限时
            sleepThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("当前时间:" + LocalDateTime.now());

        // 启动线程2
        SleepThread sleepThread2 = new SleepThread();
        System.out.println("线程2启动..." + LocalDateTime.now());
        sleepThread2.start();
        try {
            // 合并线程2 限时1秒
            sleepThread2.join(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程运行结束:" + LocalDateTime.now());
    }
}
