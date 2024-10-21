package com.mrgao.thread.base.thread;

/**
 * @author Mr.Gao
 * @apiNote:
 * @date 2024/10/19 21:23
 */
public class SynchronizedDemo {

    static final Object lock = new Object();

    static int count = 0;

    public static void main(String[] args) {
        synchronized (lock) {
            count++;
        }
    }

}
