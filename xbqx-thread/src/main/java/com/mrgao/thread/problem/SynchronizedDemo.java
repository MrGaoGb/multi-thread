package com.mrgao.thread.problem;

import java.util.concurrent.TimeUnit;

/**
 * @author Mr.Gao
 * @apiNote:synchronized原理实现
 * @date 2024/8/26 9:54
 * <p>
 * javap -c 包.类名 // 进行编译
 * </p>
 * <p>
 * monitorenter:
 * 每个对象有一个监视器锁(monitor),当 monitor 被占用时就处于锁定状态，线程执行 monitorenter 指令时尝试获取 monitor 的所有权，过程如下：
 * 1、如果monitor的进入数为0，则该线程进入monitor，然后将进入数设置为1，该线程即为 monitor 的所有者。
 * 2、如果线程已经占用 monitor ,只是重新进入，则进入 monitor 的进入数 加1
 * 3、如果其他线程已经占用了 monitor，则该线程进入阻塞状态，直到 monitor 的进入数为0，再重新尝试获取 monitor 的所有权。
 * <p>
 * monitorexit:
 * 执行monitorexit的线程必须是objectref所对应 monitor 的所有者。
 * 执行指令时，monitor的进入数减1，直到减为0，则表示线程退出 monitor，不再是这个 monitor的拥有者，其他被这个 monitor 阻塞的线程可以尝试获取
 * 这个 monitor 的所有权。
 * </p>
 * <p>
 * wait/notify方法是对象Object的方法，该方法也是依赖 monitor 对象，这也就是为什么 只有在同步的 块或方法中 采用调用 wait/notify方法；否则就会
 * 抛出异常java.lang.IllegalMonitorStateException
 * </p>
 */
public class SynchronizedDemo {


    public static void main(String[] args) {

        final Object lock = new Object();

        new Thread(() -> {
            // 直接调用 wait方法
            execWait(lock);
            // 在同步代码块中调用 wait方法
            //execWaitBySynchronized(lock);
        }).start();

        new Thread(() -> {
            // 直接调用 notify/notifyAll方法
            execNotify(lock);
            // 同步代码块中调用 notify/notifyAll方法
            //execNotifyBySynchronized(lock);
        }).start();
    }

    private static void execWait(Object lock) {
        System.out.println("调用wait方法");
        try {
            TimeUnit.SECONDS.sleep(1);
            lock.wait();
            System.out.println("wait 继续执行...!");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void execWaitBySynchronized(Object lock) {
        synchronized (lock) {
            System.out.println("调用wait方法");
            try {
                TimeUnit.SECONDS.sleep(1);
                lock.wait();
                System.out.println("wait 继续执行...!");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void execNotify(Object lock) {
        System.out.println("调用notify方法");
        try {
            TimeUnit.SECONDS.sleep(5);
            lock.notify();
            System.out.println("notify 继续执行...!");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void execNotifyBySynchronized(Object lock) {
        synchronized (lock) {
            System.out.println("调用notify方法");
            try {
                TimeUnit.SECONDS.sleep(5);
                lock.notify();
                System.out.println("notify 继续执行...!");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
