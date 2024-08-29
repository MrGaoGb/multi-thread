package com.mrgao.thread.problem;

import java.util.concurrent.locks.LockSupport;

/**
 * @Description TODO
 * @Author Mr.Gao
 * @Date 2024/8/29 23:05
 */
public class ThreadStatusDemo {

    public static void main(String[] args) throws Exception {

        // RUNNABLE与TIMED_WAITING状态转换

        // 1、sleep方法
        Thread.sleep(100);

        // 2、获得synchronized隐式锁的线程，调用带超时参数的Object.wait(long timeout)参数；

        // 3、调用带超时参数的Thread.join(long millis)方法；
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
        }, "T1").join();

        // 4、调用带超时参数的LockSupport.parkNanos(Object blocker, long deadline)方法；
        LockSupport.park();


    }
}
