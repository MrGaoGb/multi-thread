package com.mrgao.thread.deadlock;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

/**
 * @Description CAS
 * @Author Mr.Gao
 * @Date 2024/10/13 21:48
 */
public class CASDemo {
    private volatile int value = 0;
    private static Unsafe unsafe;
    private static long valueOffset;

    static {
        try {
            // 通过反射获取rt.jar包中的Unsafe类，默认Unsafe类是不能使用的
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
            valueOffset = unsafe.objectFieldOffset(CASDemo.class.getDeclaredField("value"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addOne() {
        int current;
        do {
            current = unsafe.getIntVolatile(this, valueOffset);
        } while (!unsafe.compareAndSwapInt(this, valueOffset, current, current + 1));
    }

    public static void main(String[] args) throws InterruptedException {
        final CASDemo casDemo = new CASDemo();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    casDemo.addOne();
                }
            }).start();
        }
        TimeUnit.SECONDS.sleep(5);
        System.out.println(casDemo.value);
    }
}
