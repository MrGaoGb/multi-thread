package com.mrgao.thread.threadlocal;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
        //firstView();

        // ThreadLocal不具有传递性
        //secondView();


        // InheritableThreadLocal用于解决父子线程传值问题
        //threeView();

        // 验证同一线程多次设置值的情况下，是否会存在值覆盖的问题 答案:会
        //fourView();

        // 验证TheadLocal创建map 后往集合中设置值，最后再获取当前线程存储的 map值
        //fiveView();

        // 验证直接调用remove方法是否会报错? 答案: 不会。
        //sixView();

        // 验证多个线程之间 ThreadLocal是否为同一个？
        sevenView();

    }


    /**
     * 初始ThreadLocal,且每个线程单独设置值互不影响
     */
    static void firstView() {
        // 创建一个线程
        Thread threadA = new Thread(() -> {
            threadLocal.set("threadA:" + Thread.currentThread().getName());
            System.out.println("线程A的ThreadLocal：" + threadLocal);
            System.out.println("threadA获取本地变量: " + threadLocal.get());
            // 新增删除操作
            threadLocal.remove();
            System.out.println("threadA移除本地变量后,重新获取:" + threadLocal.get());
        }, "Ta");

        // 创建第二线程
        Thread threadB = new Thread(() -> {
            threadLocal.set("threadB:" + Thread.currentThread().getName());
            System.out.println("线程B的ThreadLocal：" + threadLocal);
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

    private static final ThreadLocal<Map<Object, Object>> resources = new ThreadLocal<>();

    /**
     * 验证TheadLocal创建map 后往集合中设置值，最后再获取当前线程存储的 map值
     */
    static void fiveView() {
        String actualKey = UUID.randomUUID().toString().replaceAll("-", "");
        System.out.println("用户key：" + actualKey);
        String value = "Hello ThreadLocal";
        Map<Object, Object> map = resources.get();
        // set ThreadLocal Map if none found
        if (map == null) {
            System.out.println("map为空,进行new创建map!");
            map = new HashMap<>(4);
            resources.set(map);
        }
        // 存储新的值 返回旧值
        Object oldValue = map.put(actualKey, value);
        System.out.println("第一次打印:" + oldValue);
        value = "Reset ThreadLocal Val";
        // 第二次重新设置值
        oldValue = map.put(actualKey, value);
        System.out.println("第二次打印:" + oldValue);
        System.out.println("打印当前线程的值:" + resources.get());
    }

    /**
     * 验证直接调用remove方法是否会报错呢?
     */
    static void sixView() {
        resources.remove();
    }

    /**
     * 验证ThreadLocal是否为同一个
     */
    static void sevenView() {
        // 主线程设置值
        threadLocal.set("Main:" + Thread.currentThread().getName());
        System.out.println(threadLocal.get());

        // 创建一个线程
        Thread threadA = new Thread(() -> {
            threadLocal.set("threadA:" + Thread.currentThread().getName());
            System.out.println("线程A的ThreadLocal：" + threadLocal);
            System.out.println("threadA获取本地变量: " + threadLocal.get());
            // 新增删除操作
            threadLocal.remove();
            System.out.println("threadA移除本地变量后,重新获取:" + threadLocal.get());
        }, "Ta");
        // 启动线程
        threadA.start();

    }

}
