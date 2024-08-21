package com.mrgao.thread.pool;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 单线程模式
 */
public class TimerDemo {
    public static void main(String[] args) {

        Timer timer = new Timer();
        System.out.println("开始启动:"+LocalDateTime.now());
        final Integer[] num = {0};
        /**
         * @param task: 任务线程
         * @param delay: 任务执行前的延时时间
         * @param period: 任务间隔的延时时间
         */
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (num[0] == 20){
                    int n = 10/0; // num等于20时 抛出异常，看程序是否终止运行
                    //TODO 经过运行 会抛出异常 ArithmeticException: / by zero，该线程会直接终止运行
                }
                System.out.println("TimerTask...,开始执行了！ "+ LocalDateTime.now());
                num[0]++;
            }
        },10000,2000);


//        Thread.sleep(10000);
//        timer.cancel();

    }
}
