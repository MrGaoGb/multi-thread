package com.mrgao.thread.deadlock;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @Description 解决ABA问题
 * @Author Mr.Gao
 * @Date 2024/10/13 22:14
 */
public class ABADemo {

    private static AtomicStampedReference<Integer> atomicStampedRef = new AtomicStampedReference<>(1, 0);

    public static void main(String[] args) throws InterruptedException {
        System.out.println("初始值：" + atomicStampedRef.getReference() + "，版本号：" + atomicStampedRef.getStamp());

        // 线程 1 先执行一次 CAS 操作，期望值为 1，新值为 2，版本号为 0
        Thread thread1 = new Thread(() -> {
            int stamp = atomicStampedRef.getStamp();
            System.out.println(Thread.currentThread().getName() + ": 版本号:" + stamp);
            atomicStampedRef.compareAndSet(1, 2, stamp, stamp + 1);
        });

        // 线程 2 先 sleep 1 秒，让线程 1 先执行一次 CAS 操作，然后再执行一次 CAS 操作，期望值为 2，新值为 1，版本号为 1
        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int stamp = atomicStampedRef.getStamp();
            System.out.println(Thread.currentThread().getName() + ": 版本号:" + stamp);
            atomicStampedRef.compareAndSet(2, 1, stamp, stamp + 1);
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("最终值：" + atomicStampedRef.getReference() + "，版本号：" + atomicStampedRef.getStamp());
    }

}
