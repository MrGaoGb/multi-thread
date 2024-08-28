package com.mrgao.thread.problem.transfer;


/**
 * @Description TODO
 * @Author Mr.Gao
 * @Date 2024/8/28 23:30
 */
public class SynchronizedTransferAccountDemo {

    public static void main(String[] args) throws InterruptedException {
//        CountDownLatch countDownLatch = new CountDownLatch(2);

        Object lock = new Object();

        // 处理无限循环中
        for (int i = 0; i < 20000; i++) {

            // 账户A、B、C账户余额分别为200, A给B转账100 然后B给C转账100，最后输出各自余额还剩多少
//            TransferAccount A = new TransferAccount(200);
//            TransferAccount B = new TransferAccount(200);
//            TransferAccount C = new TransferAccount(200);
            TransferAccount A = new TransferAccount(lock,200);
            TransferAccount B = new TransferAccount(lock,200);
            TransferAccount C = new TransferAccount(lock,200);
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
//                A.transfer(B, 100);// 存在并发问题
                A.transfer2(B, 100);// 存在并发问题
//                A.transferUpgrade(B,100);// 升级版
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
//                B.transfer(C, 100);// 存在并发问题
                B.transfer2(C, 100);
//                B.transferUpgrade(C,100);// 升级版
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
                break;
            }

        }


    }
}
