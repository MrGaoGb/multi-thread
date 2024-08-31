package com.mrgao.thread.problem.transfer;


import org.springframework.util.StopWatch;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description TODO
 * @Author Mr.Gao
 * @Date 2024/8/28 23:30
 */
public class SynchronizedTransferAccountDemo {

    private static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
//        CountDownLatch countDownLatch = new CountDownLatch(2);
        SynchronizedTransferAccountDemo transferAccountDemo = new SynchronizedTransferAccountDemo();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        System.out.println("开始时间:" + LocalDateTime.now());
        // 处理无限循环中
        for (int i = 0; i < 100000; i++) {

            // 账户A、B、C账户余额分别为200, A给B转账100 然后B给C转账100，最后输出各自余额还剩多少

            // 第一种引出问题
//            boolean execResult = transferAccountDemo.planOne();

            // 使用同一把锁锁定待操作的资源
            //boolean execResult = transferAccountDemo.planTwo();

            // 使用同一把锁锁定待操作的资源
            //boolean execResult = transferAccountDemo.planThree();

            // 结合CountDownLatch等线程执行完之后再继续执行
            boolean execResult = transferAccountDemo.planFour();

            if (execResult) {
                break;
            }
        }
        stopWatch.stop();
        System.out.println("开始时间:" + LocalDateTime.now() + ",耗时:" + stopWatch.getTotalTimeMillis() + "ms!");
    }


    /**
     * 第一种方式引发问题
     */
    public boolean planOne() {
        //账户A、B、C账户余额分别为200, A给B转账100 然后B给C转账100，最后输出各自余额还剩多少
        TransferAccount A = new TransferAccount(200);
        TransferAccount B = new TransferAccount(200);
        TransferAccount C = new TransferAccount(200);

        // A转账B 转账100元
        Thread threadA = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ",开始发起转账操作!");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            A.transfer(B, 100);// 存在并发问题
            System.out.println(Thread.currentThread().getName() + ",发起转账操作完成!");
//                countDownLatch.countDown(); //表示数量 -1
        }, "threadA");
        // B转账C 转账100元
        Thread threadB = new Thread(() -> {
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
            System.out.println(Thread.currentThread().getName() + ",开始发起转账操作!");
            B.transfer(C, 100);// 存在并发问题
            System.out.println(Thread.currentThread().getName() + ",发起转账操作完成!");
//                countDownLatch.countDown(); //表示数量 -1
        }, "threadB");


        // 启动线程A、B
        threadA.start();
        threadB.start();


        // 线程执行完后 再继续执行
//            countDownLatch.await();

        // 异常并发情况
        if (B.getBalance() == 300 || B.getBalance() == 100) {
            System.out.println("====================输出A余额(转账后):" + A.getBalance());
            System.out.println("====================输出B余额(转账后):" + B.getBalance());
            System.out.println("====================输出C余额(转账后):" + C.getBalance());
            return true;
        }

        return false;
    }

    /**
     * 第二种方式优化
     */
    public boolean planTwo() {
        //账户A、B、C账户余额分别为200, A给B转账100 然后B给C转账100，最后输出各自余额还剩多少
        TransferAccount A = new TransferAccount(lock, 200);
        TransferAccount B = new TransferAccount(lock, 200);
        TransferAccount C = new TransferAccount(lock, 200);
        System.out.println("输出A余额(转账前):" + A.getBalance());
        System.out.println("输出B余额(转账前):" + B.getBalance());
        System.out.println("输出C余额(转账前):" + C.getBalance());
        // A转账B 转账100元
        Thread threadA = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ",开始发起转账操作!");
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
            A.transfer2(B, 100);// 存在并发问题
            System.out.println(Thread.currentThread().getName() + ",发起转账操作完成!");
//                countDownLatch.countDown(); //表示数量 -1
        }, "threadA");
        // B转账C 转账100元
        Thread threadB = new Thread(() -> {
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
            System.out.println(Thread.currentThread().getName() + ",开始发起转账操作!");
            B.transfer2(C, 100);// 存在并发问题
            System.out.println(Thread.currentThread().getName() + ",发起转账操作完成!");
//                countDownLatch.countDown(); //表示数量 -1
        }, "threadB");


        // 启动线程A、B
        threadA.start();
        threadB.start();


        // 线程执行完后 再继续执行
//            countDownLatch.await();

        // 异常并发情况
        if (B.getBalance() == 300 || B.getBalance() == 100) {
            System.out.println("====================输出A余额(转账后):" + A.getBalance());
            System.out.println("====================输出B余额(转账后):" + B.getBalance());
            System.out.println("====================输出C余额(转账后):" + C.getBalance());
            return true;
        }

        return false;
    }

    /**
     * 第三种方式优化
     */
    public boolean planThree() {
        //账户A、B、C账户余额分别为200, A给B转账100 然后B给C转账100，最后输出各自余额还剩多少
        TransferAccount A = new TransferAccount(200);
        TransferAccount B = new TransferAccount(200);
        TransferAccount C = new TransferAccount(200);
        System.out.println("输出A余额(转账前):" + A.getBalance());
        System.out.println("输出B余额(转账前):" + B.getBalance());
        System.out.println("输出C余额(转账前):" + C.getBalance());

        // A转账B 转账100元
        Thread threadA = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ",开始发起转账操作!");
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
            A.transferUpgrade(B, 100);// 存在并发问题
            System.out.println(Thread.currentThread().getName() + ",发起转账操作完成!");
//                countDownLatch.countDown(); //表示数量 -1
        }, "threadA");

        // B转账C 转账100元
        Thread threadB = new Thread(() -> {
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
            System.out.println(Thread.currentThread().getName() + ",开始发起转账操作!");
            B.transferUpgrade(C, 100);// 存在并发问题
            System.out.println(Thread.currentThread().getName() + ",发起转账操作完成!");
//                countDownLatch.countDown(); //表示数量 -1
        }, "threadB");


        // 启动线程A、B
        threadA.start();
        threadB.start();


        // 线程执行完后 再继续执行
//            countDownLatch.await();

        // 异常并发情况
        if (B.getBalance() == 300 || B.getBalance() == 100) {
            System.out.println("====================输出A余额(转账后):" + A.getBalance());
            System.out.println("====================输出B余额(转账后):" + B.getBalance());
            System.out.println("====================输出C余额(转账后):" + C.getBalance());
            return true;
        }

        return false;
    }

    /**
     * 第四种方式优化
     * <p>
     * 通过CountDownLatch解决在线程执行完毕后，再进行打印结果，原因是通过调用start方法之后，不能确保线程确实已经被执行完了，只有线程本身真正执行完毕后，
     * 即就是以最后的结果为准
     * </p>
     */
    public boolean planFour() {
        //账户A、B、C账户余额分别为200, A给B转账100 然后B给C转账100，最后输出各自余额还剩多少
        CountDownLatch a2bCountDownLatch = new CountDownLatch(1);
        CountDownLatch b2cCountDownLatch = new CountDownLatch(1);

        TransferAccount A = new TransferAccount(200);
        TransferAccount B = new TransferAccount(200);
        TransferAccount C = new TransferAccount(200);

        // A转账B 转账100元
        Thread threadA = new Thread(() -> {
            A.transferUpgrade(B, 100);// 存在并发问题
            a2bCountDownLatch.countDown();
        }, "threadA");

        // B转账C 转账100元
        Thread threadB = new Thread(() -> {
            B.transferUpgrade(C, 100);// 存在并发问题
            b2cCountDownLatch.countDown();
        }, "threadB");


        // 启动线程A、B
        threadA.start();
        threadB.start();

        // 等待线程A执行完
        try {
            a2bCountDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // 等待线程B执行完
        try {
            b2cCountDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // 异常并发情况
        if (A.getBalance().equals(100) && B.getBalance().equals(200) && C.getBalance().equals(300)) {
//            String printVal = String.format("A->B->C 转账成功: A的余额为:%d , B的余额为:%d,C的余额为:%d", A.getBalance(), B.getBalance(), C.getBalance());
//            System.out.println(Thread.currentThread().getName() + " " + printVal);
            return false;
        } else {
            String printVal = String.format("A->B->C 转账失败: A的余额为:%d , B的余额为:%d,C的余额为:%d", A.getBalance(), B.getBalance(), C.getBalance());
            System.out.println(Thread.currentThread().getName() + ": " + printVal);
            return true;
        }
    }

}
