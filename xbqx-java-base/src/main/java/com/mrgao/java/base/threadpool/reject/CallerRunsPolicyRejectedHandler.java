package com.mrgao.java.base.threadpool.reject;

import com.mrgao.java.base.threadpool.IRejectHandler;
import com.mrgao.java.base.threadpool.MyThreadPool;

/**
 * @author Mr.Gao
 * @apiNote:由当前线程自己调用
 * @date 2025/4/21 16:10
 */
public class CallerRunsPolicyRejectedHandler implements IRejectHandler {

    @Override
    public void reject(Runnable t, MyThreadPool pool) {
        t.run();// 执行当前线程
        System.out.println(Thread.currentThread().getName() + ":当前线程执行了任务：" + t);
    }
}
