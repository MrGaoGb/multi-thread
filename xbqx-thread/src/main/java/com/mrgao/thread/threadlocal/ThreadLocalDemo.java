package com.mrgao.thread.threadlocal;

/**
 * ThreadLocal：
 */
public class ThreadLocalDemo {


    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    /**
     * 可用于解决父子线程传值问题
     */
    private static final ThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();

    public static void main(String[] args) {

        // 初识ThreadLocal,且每个线程单独设置值互不影响
        // firstView();

        // ThreadLocal不具有传递性
        //secondView();


        // InheritableThreadLocal用于解决父子线程传值问题
        //threeView();

        // 验证同一线程多次设置值的情况下，是否会存在值覆盖的问题
        fourView();
    }


    /**
     * 初始ThreadLocal,且每个线程单独设置值互不影响
     */
    static void firstView() {
        // 创建一个线程
        Thread threadA = new Thread(() -> {
            threadLocal.set("threadA:" + Thread.currentThread().getName());
            System.out.println("threadA获取本地变量: " + threadLocal.get());
            // 新增删除操作
            threadLocal.remove();
            System.out.println("threadA移除本地变量后,重新获取:" + threadLocal.get());
        }, "Ta");

        // 创建第二线程
        Thread threadB = new Thread(() -> {
            threadLocal.set("threadB:" + Thread.currentThread().getName());
            System.out.println("threadB获取本地变量: " + threadLocal.get());
            System.out.println("threadB未移除本地变量,重新获取:" + threadLocal.get());
        }, "Tb");

        // 启动线程
        threadA.start();
        threadB.start();
    }

    /**
     * ThreadLocal不具有传递性
     */
    static void secondView() {
        // ThreadLocal不具有传递性
        // 同一个ThreadLocal中，父线程设置完值后，子线程获取不到
        threadLocal.set("Main 设置的值:" + Thread.currentThread().getName());
        // 创建一个子线程获取父线程设置的值
        Thread threadB = new Thread(() -> {
            System.out.println("threadB获取本地变量: " + threadLocal.get());
        }, "Tb");

        // 启动线程
        threadB.start();

        //在主线程中获取值
        System.out.println("主线程获取值：" + threadLocal.get());
    }

    /**
     * InheritableThreadLocal用于解决父子线程传值问题
     */
    static void threeView() {
        // 主线程设置值
        inheritableThreadLocal.set("inheritableThreadLocal 设置的值");

        // 创建一个子线程
        Thread subThread = new Thread(() -> {
            // 子线程获取父线程设置的值
            String val = inheritableThreadLocal.get();
            System.out.println("子线程获取父线程设置的值:" + val);
        });

        // 启动子线程
        subThread.start();

        // 主线程获取值
        System.out.println("主线程获取值:" + inheritableThreadLocal.get());
    }

    /**
     * 验证同一线程多次设置值的情况下，是否会存在值覆盖的问题
     * <p>答案是：会存在值覆盖</p>
     */
    static void fourView() {
        // 首次设置值
        threadLocal.set("first Val");
        System.out.println(threadLocal.get());
        // 第二次设置值
        threadLocal.set("second Val");
        System.out.println(threadLocal.get());

    }

}
