package com.mrgao.thread.threadlocal;

/**
 * 通过join保证线程执行顺序
 * <p>
 * 一直等待线程超时或终止
 * </p>
 */
public class Thread_join {
    public static void main(String[] args) throws Exception {

        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("A");
        });
        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("B");
        });
        Thread t3 = new Thread(() -> {
            System.out.println("C");
        });

        t1.start();
        t1.join();

        t2.start();
        t2.join();

        t3.start();
    }
}
