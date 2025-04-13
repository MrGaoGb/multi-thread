package com.mrgao.thread.condition;

import java.time.LocalDateTime;
import java.util.concurrent.locks.LockSupport;

/**
 * @author Mr.Gao
 * @apiNote:LockSupport无锁的原因如下 <p>
 * 1、许可机制：线程在调用park()方法时，会获取一个许可，如果当前没有许可，则当前线程阻塞，直到获取到许可为止。
 * - 线程私有性：每个线程内部维护一个二进制许可标记（0表示没有许可，1表示有许可）
 * - unpark(Thread): 将目标线程的许可设为1(若是1则无变化)。
 * - park(): 检查许可，
 * - 若许可为1，立即消费许可（置为0）并返回。
 * - 若许可为0，则当前线程阻塞直到许可被设为1。
 * - 原子性：park()和unpark()方法都是原子操作，不会被中断（因为都是基于UnSafe类实现，无需锁即可保证线程安全）。
 * 2、直接操作线程状态：
 * - 绕过监视器锁：LockSupport直接与JVM线程调度机制交互，通过操作系统原语（如 pthread_cond_wait 或 futex）挂起或唤醒线程，
 * 无需依赖 Java 对象的监视器锁（synchronized）。传统 wait()/notify() 需要锁是因为它们基于对象监视器，
 * 而 LockSupport 绕过了这一层抽象。
 * - 精准控制：
 * - unpark(Thread)：可以指定目标线程，无需通过共享对象锁协调线程。这种避免了锁竞争，提高了灵活性。
 *
 * </p>
 * @date 2025/4/1 11:05
 */
public class LockSupportDemo {
    public static void main(String[] args) throws InterruptedException {

//        CountDownLatch countDownLatch = new CountDownLatch(1);

        Thread thread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ":" + "开始运行...date:" + LocalDateTime.now());
            //LockSupport.park(); // 表示会一直阻塞, 需要等待被唤醒
            //LockSupport.parkNanos(2_000_000_000L); // 表示会阻塞2s，两秒之后会被主动唤醒(或通过unpark被动唤醒)
            LockSupport.parkUntil(5000);
            System.out.println(Thread.currentThread().getName() + ":" + "结束运行... date:" + LocalDateTime.now());
//             countDownLatch.countDown();// 线程执行结束
        }, "T1");

        // 启动线程
        thread.start();

        //LockSupport.unpark(thread);// 唤醒线程T1
        // 主线程阻塞
//        countDownLatch.await();
        Thread.sleep(1000);// 主线程阻塞5s
        System.out.println(Thread.currentThread().getName() + ":" + "结束运行... date:" + LocalDateTime.now());
        LockSupport.unpark(thread);// 唤醒线程T1
//        System.out.println(Thread.currentThread().getName() + ":" + "结束运行... date:" + LocalDateTime.now());

    }
}
