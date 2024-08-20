package com.mrgao.thread.threadlocal;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Mr.Gao
 * @date 2024/8/20 11:12
 * @apiNote:解决多线程SimpleDateFormat的线程安全问题
 */
public class SimpleDateFormatOptimizeDemo {

    /**
     * 执行总次数
     */
    private static final int EXECUTE_COUNT = 1000;
    /**
     * 同时运行的线程数量
     */
    private static final int THREAD_COUNT = 20;
    /**
     * 时间格式
     */
    private static final String FORMAT = "yyyy-MM-dd";

    public static void main(String[] args) throws Exception {

        // 由于Calendar是线程不安全的，因此第一种解决方式为每个线程都创建一个new SimpleDateFormat(FORMAT)对象;
        //one();

        // synchronized方式实现
        //two();

        // Lock锁方式
        //three();

        // ThreadLocal方式
        //four();

        // DateTimeFormatter方式
        five();

    }


    /**
     * 由于Calendar是线程不安全的，因此第一种解决方式为每个线程都创建一个new SimpleDateFormat(FORMAT)对象;
     *
     * @throws Exception
     */
    private static void one() throws Exception {
        final Semaphore semaphore = new Semaphore(THREAD_COUNT);
        final CountDownLatch countDownLatch = new CountDownLatch(EXECUTE_COUNT);
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < EXECUTE_COUNT; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    try {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT);
                        simpleDateFormat.parse("2020-01-01");
                    } catch (ParseException e) {
                        System.out.println("线程：" + Thread.currentThread().getName() + " 格式化日期失败");
                        e.printStackTrace();
                        System.exit(1);
                    } catch (NumberFormatException e) {
                        System.out.println("线程：" + Thread.currentThread().getName() + " 格式化日期失败");
                        e.printStackTrace();
                        System.exit(1);
                    }
                    semaphore.release();
                } catch (InterruptedException e) {
                    System.out.println("信号量发生错误");
                    e.printStackTrace();
                    System.exit(1);
                }
                countDownLatch.countDown();
            });
        }

        // 阻塞等待
        countDownLatch.await();
        executorService.shutdown();
        System.out.println("所有线程格式化日期成功");
    }

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT);

    /**
     * synchronized方式实现
     *
     * @throws Exception
     */
    private static void two() throws Exception {
        final Semaphore semaphore = new Semaphore(THREAD_COUNT);
        final CountDownLatch countDownLatch = new CountDownLatch(EXECUTE_COUNT);
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < EXECUTE_COUNT; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    try {
                        synchronized (simpleDateFormat) {
                            simpleDateFormat.parse("2020-01-01");
                        }
                    } catch (ParseException e) {
                        System.out.println("线程：" + Thread.currentThread().getName() + " 格式化日期失败");
                        e.printStackTrace();
                        System.exit(1);
                    } catch (NumberFormatException e) {
                        System.out.println("线程：" + Thread.currentThread().getName() + " 格式化日期失败");
                        e.printStackTrace();
                        System.exit(1);
                    }
                    semaphore.release();
                } catch (InterruptedException e) {
                    System.out.println("信号量发生错误");
                    e.printStackTrace();
                    System.exit(1);
                }
                countDownLatch.countDown();
            });
        }

        // 阻塞等待
        countDownLatch.await();
        executorService.shutdown();
        System.out.println("所有线程格式化日期成功");

    }

    //Lock对象
    private static Lock lock = new ReentrantLock();

    /**
     * lock锁方式
     */
    private static void three() throws Exception {
        final Semaphore semaphore = new Semaphore(THREAD_COUNT);
        final CountDownLatch countDownLatch = new CountDownLatch(EXECUTE_COUNT);
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < EXECUTE_COUNT; i++) {
            executorService.execute(() -> {
                // 获取锁
                lock.lock();
                try {
                    semaphore.acquire();
                    try {
                        simpleDateFormat.parse("2020-01-01");
                    } catch (ParseException e) {
                        System.out.println("线程：" + Thread.currentThread().getName() + " 格式化日期失败");
                        e.printStackTrace();
                        System.exit(1);
                    } catch (NumberFormatException e) {
                        System.out.println("线程：" + Thread.currentThread().getName() + " 格式化日期失败");
                        e.printStackTrace();
                        System.exit(1);
                    }
                    semaphore.release();
                } catch (InterruptedException e) {
                    System.out.println("信号量发生错误");
                    e.printStackTrace();
                    System.exit(1);
                } finally {
                    // 释放锁
                    lock.unlock();
                }
                countDownLatch.countDown();
            });
        }

        // 阻塞等待
        countDownLatch.await();
        executorService.shutdown();
        System.out.println("所有线程格式化日期成功");
    }

    private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(FORMAT);
        }
    };

    /**
     * ThreadLocal方式
     */
    private static void four() throws Exception {
        final Semaphore semaphore = new Semaphore(THREAD_COUNT);
        final CountDownLatch countDownLatch = new CountDownLatch(EXECUTE_COUNT);
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < EXECUTE_COUNT; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    try {
                        threadLocal.get().parse("2020-01-01");
                    } catch (ParseException e) {
                        System.out.println("线程：" + Thread.currentThread().getName() + " 格式化日期失败");
                        e.printStackTrace();
                        System.exit(1);
                    } catch (NumberFormatException e) {
                        System.out.println("线程：" + Thread.currentThread().getName() + " 格式化日期失败");
                        e.printStackTrace();
                        System.exit(1);
                    }
                    semaphore.release();
                } catch (InterruptedException e) {
                    System.out.println("信号量发生错误");
                    e.printStackTrace();
                    System.exit(1);
                }
                countDownLatch.countDown();
            });
        }

        // 阻塞等待
        countDownLatch.await();
        executorService.shutdown();
        System.out.println("所有线程格式化日期成功");

    }

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(FORMAT);

    /**
     * DateTimeFormatter方式
     */
    private static void five() throws Exception {
        final Semaphore semaphore = new Semaphore(THREAD_COUNT);
        final CountDownLatch countDownLatch = new CountDownLatch(EXECUTE_COUNT);
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < EXECUTE_COUNT; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    try {
                        LocalDate.parse("2024-08-20", dateTimeFormatter);
                    } catch (NumberFormatException e) {
                        System.out.println("线程：" + Thread.currentThread().getName() + " 格式化日期失败");
                        e.printStackTrace();
                        System.exit(1);
                    }
                    semaphore.release();
                } catch (InterruptedException e) {
                    System.out.println("信号量发生错误");
                    e.printStackTrace();
                    System.exit(1);
                }
                countDownLatch.countDown();
            });
        }

        // 阻塞等待
        countDownLatch.await();
        executorService.shutdown();
        System.out.println("所有线程格式化日期成功");
    }
}
