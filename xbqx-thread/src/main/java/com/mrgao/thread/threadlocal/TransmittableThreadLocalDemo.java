package com.mrgao.thread.threadlocal;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Mr.Gao
 * @apiNote: 解决线程池场景下变量的传值问题
 * @date 2024/9/13 15:52
 */
public class TransmittableThreadLocalDemo {


    public static void main(String[] args) throws Exception {

        // InheritableThreadLocal在线程池中应用
        itlTestPool();

        // 通过线程池复用线程的方式
        //ttlTest();
    }

    /**
     * 线程池复用线程
     *
     * @throws Exception
     */
    static void ttlTest() throws Exception {
        TransmittableThreadLocal<String> transmittableThreadLocal = new TransmittableThreadLocal<>();
        ExecutorService executorService = null;
        ExecutorService ttlExecutorService = null;
        try {
            transmittableThreadLocal.set("Main Val");

            // 使用JDK 创建线程池 只有一个线程
            executorService = Executors.newFixedThreadPool(1);

            /**
             * 使用 TtlExecutors 创建一个 Ttl 框架封装的线程池
             */
            ttlExecutorService = TtlExecutors.getTtlExecutorService(executorService);

            CountDownLatch c1 = new CountDownLatch(1);
            CountDownLatch c2 = new CountDownLatch(1);

            ttlExecutorService.submit(() -> {
                System.out.println("子线程1: " + Thread.currentThread().getName() + ", 获取参数: " + transmittableThreadLocal.get());
                sleep(5);
                c1.countDown();
            });
            // 线程1执行未执行完，需要进行等待
            c1.await();

            // 线程1执行结束,重新设置值
            transmittableThreadLocal.set("Enhance Main Val");
            System.out.println("Main线程: " + Thread.currentThread().getName() + ", 获取参数: " + transmittableThreadLocal.get());
            ttlExecutorService.submit(() -> {
                System.out.println("子线程2: " + Thread.currentThread().getName() + ", 获取参数: " + transmittableThreadLocal.get());
                sleep(3);
                c2.countDown();
            });
            // 线程2未执行完时，需要等待
            c2.await();
            System.out.println("Main线程: " + Thread.currentThread().getName() + ", 获取参数: " + transmittableThreadLocal.get());
        } finally {
            // 关闭线程池
            if (null != executorService) {
                executorService.shutdown();
            }
            if (null != ttlExecutorService) {
                ttlExecutorService.shutdown();
            }
            // 清空threadLocal内存
            transmittableThreadLocal.remove();
        }
    }

    /**
     * InheritableThreadLocal在线程池中应用
     *
     * @throws Exception
     */
    static void itlTestPool() throws Exception {

        InheritableThreadLocal<String> itl = new InheritableThreadLocal<>();

        try {
            itl.set("Main Val");

            // 创建线程池
            ExecutorService executorService = Executors.newFixedThreadPool(1);

            CountDownLatch c1 = new CountDownLatch(1);
            CountDownLatch c2 = new CountDownLatch(1);

            executorService.submit(() -> {
                System.out.println("子线程1: " + Thread.currentThread().getName() + ", 获取参数: " + itl.get());
                sleep(5);
                c1.countDown();
            });
            // 线程1执行未执行完，需要进行等待
            c1.await();

            // 线程1执行结束,重新设置值
            itl.set("Enhance Main Val");
            System.out.println("Main线程: " + Thread.currentThread().getName() + ", 获取参数: " + itl.get());
            executorService.submit(() -> {
                System.out.println("子线程2: " + Thread.currentThread().getName() + ", 获取参数: " + itl.get());
                sleep(3);
                c2.countDown();
            });
            // 线程2未执行完时，需要等待
            c2.await();
            System.out.println("Main线程: " + Thread.currentThread().getName() + ", 获取参数: " + itl.get());

            // 关闭线程池
            executorService.shutdown();
        } finally {
            itl.remove();
        }
    }

    /**
     * 延时处理，默认单位时间：秒
     *
     * @param timeout
     */
    private static void sleep(long timeout) {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
