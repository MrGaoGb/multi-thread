package com.mrgao.java.base.threadpool.reject;

import com.mrgao.java.base.threadpool.IRejectHandler;
import com.mrgao.java.base.threadpool.MyThreadPool;

/**
 * @Description 抛出异常
 * @Author Mr.Gao
 * @Date 2025/4/14 22:52
 */
public class ThrowExceptionRejectedHandler implements IRejectHandler {
    @Override
    public void reject(Runnable t, MyThreadPool pool) {
        throw new IllegalStateException("任务" + t.toString() + " 被拒绝了..." + pool.toString());
    }
}
