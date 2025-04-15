package com.mrgao.java.base.gc;

import java.lang.ref.WeakReference;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Mr.Gao
 * @apiNote:
 * @date 2025/4/15 10:08
 */
public class MainGc {
    public static void main(String[] args) {

        // 测试弱引用 Cat 是否被回收
        //testCatGc();

        // 测试弱引用 ThreadPoolExecutor 是否被回收
        /**
         * 1、如果线程池ThreadPoolExecutor没有任务时，executor = null之后就会被GC回收
         * 2、如果线程池ThreadPoolExecutor有任务时，executor = null之后，线程池中的线程不会被回收，而是一直存在，直到线程池中的线程执行完毕，
         *    才会被回收，但是线程池使用Worker线程来执行核心线程的任务，所以不会被回收
         */
        testThreadPoolGc();

    }

    /**
     * 测试弱引用 ThreadPoolExecutor 是否被回收
     */
    public static void testThreadPoolGc() {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                6,
                8,
                10,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(8),
                new MyThreadFactory() // 自定义线程工厂
        );

        executor.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + ":线程执行中.......");
        });

        WeakReference<ThreadPoolExecutor> weakReference = new WeakReference<>(executor);
        // 将 executor 置为null
        executor = null;
        System.gc();// 触发gc
        if (weakReference.get() == null) {
            System.out.println("executor被回收了");
        } else {
            System.out.println("executor没有被回收");
        }
    }


    /**
     * 测试弱引用 Cat 是否被回收
     */
    public static void testCatGc() {
        Cat cat = new Cat();
        WeakReference<Cat> weakReference = new WeakReference<>(cat);
        System.out.println("垃圾回收前:" + weakReference + ", get()=" + weakReference.get());
        System.gc();// 启动调用gc回收器
        System.out.println("垃圾回收后:" + weakReference + ", get()=" + weakReference.get());

        // 将引用cat对象置为null
        cat = null;
        System.gc();
        System.out.println("cat置为null且再次触发垃圾回收后:" + weakReference + ", get()=" + weakReference.get());
        if (weakReference.get() == null) {
            System.out.println("cat被回收了");
        }
    }
}

class MyThreadFactory implements ThreadFactory {
    /**
     * 线程池工厂中：Thread ---> Worker ---> ThreadFactory
     *
     * @param r a runnable to be executed by new thread instance
     * @return
     */
    @Override
    public Thread newThread(Runnable r) {
        Runnable runnable = () -> {
            System.out.println("线程真正实现开始执行...");
            //r.run();// 线程真正实现开始执行...
            System.out.println("线程真正实现执行完毕...");
        };
        return new Thread(runnable, "mythread");
    }
}

class Cat {
}
