package com.mrgao.java.base.threadpool.reject;

import com.mrgao.java.base.threadpool.IRejectHandler;
import com.mrgao.java.base.threadpool.MyThreadPool;

/**
 * @Description 什么也不做
 * @Author Mr.Gao
 * @Date 2025/4/15 0:22
 */
public class NoThingTodoRejectHandler implements IRejectHandler {
    @Override
    public void reject(Runnable t, MyThreadPool pool) {
        // Nothing todo
    }
}
