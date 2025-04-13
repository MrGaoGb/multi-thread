package com.mrgao.java.base.scheduled;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Description 定时任务启动类验证
 * @Author Mr.Gao
 * @Date 2025/4/13 17:20
 */
public class ScheduledMain {

    public static void main(String[] args) throws Exception {
        MyScheduledService myScheduledService = new MyScheduledService();
        // 定义一个时间格式: 时分秒 毫秒值
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss SSS");
        // 创建一个定时任务 ， 执行频率1s
        System.out.println("添加一个100ms的循环延时任务!");
        myScheduledService.scheduledTask(() -> {
            System.out.println(LocalDateTime.now().format(dateTimeFormatter) + "：这是每100ms执行的任务");
        }, 100);

        // 主线程延时2s后 再新增一个定时任务
        Thread.sleep(500);

        System.out.println("添加一个200ms的循环延时任务!");
        myScheduledService.scheduledTask(() -> {
            System.out.println(LocalDateTime.now().format(dateTimeFormatter) + "：这是每200ms执行的任务");
        }, 200);

        Thread.sleep(500);
        System.out.println("添加一个50ms的循环延时任务!");

        myScheduledService.scheduledTask(() -> {
            System.out.println(LocalDateTime.now().format(dateTimeFormatter) + "：这是每50ms执行的任务");
        }, 50);
    }

}
