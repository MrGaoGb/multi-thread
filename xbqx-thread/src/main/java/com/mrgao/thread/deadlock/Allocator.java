package com.mrgao.thread.deadlock;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 用于破坏占有且等待条件
 * @Author Mr.Gao
 * @Date 2024/10/13 11:31
 */
public class Allocator {

    private static Allocator instance = new Allocator();

    private Allocator() {
    }

    public static Allocator getInstance() {
        return instance;
    }

    // 该容器 用来存储锁
    private List<Object> als = new ArrayList<>();

    // 一次性申请所有资源
    synchronized boolean allocate(Object from, Object to) {
        if (als.contains(from) || als.contains(to)) {
            return false;
        }
        als.add(from);
        als.add(to);
        return true;
    }

    // 释放持有的锁资源
    synchronized void release(Object from, Object to) {
        als.remove(from);
        als.remove(to);
    }
}
