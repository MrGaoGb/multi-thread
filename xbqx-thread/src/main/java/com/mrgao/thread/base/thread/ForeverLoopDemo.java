package com.mrgao.thread.base.thread;

/**
 * @author Mr.Gao
 * @apiNote: volatile关键字的线程可见性Demo
 * @date 2024/10/20 14:37
 */
public class ForeverLoopDemo {

   /*volatile */static boolean flag = false;

    public static void main(String[] args) {

        // 创建线程1
        new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            flag = true;
            System.out.println(Thread.currentThread().getName() + ",将flag变量修改为true！");
        }, "T1").start();

        // 创建线程2
        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + ",获取flag变量:" + flag);
        }, "T2").start();

        // 创建线程3
        new Thread(() -> {
            int c = 0;
            while (!flag) {
                c++;
            }
            System.out.println(Thread.currentThread().getName() + ",输出变量c的值:" + c);
        }, "T3").start();
    }

}
