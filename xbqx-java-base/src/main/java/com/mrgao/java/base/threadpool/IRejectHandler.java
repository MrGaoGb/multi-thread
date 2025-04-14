package com.mrgao.java.base.threadpool;

/**
 * @Description 定义拒绝策略
 * @Author Mr.Gao
 * @Date 2025/4/14 22:50
 */
public interface IRejectHandler {

    /**
     * 拒绝策略
     *
     * @param t
     * @param pool
     */
    void reject(Runnable t, MyThreadPool pool);

}
