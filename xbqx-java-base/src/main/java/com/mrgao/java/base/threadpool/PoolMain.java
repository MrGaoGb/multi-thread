package com.mrgao.java.base.threadpool;

/**
 * @author Mr.Gao
 * @apiNote: 实现线程池功能
 * @date 2025/4/14 17:02
 */
public class PoolMain {

    public static void main(String[] args) {
        // 创建线程池对象(自定义实现)
        MyThreadPool pool = new MyThreadPool();
        try {
            // 提交任务
            for (int i = 0; i < 10; i++) {
                final int fi = i;
                pool.execute(() -> {
                    try {
                        // 延时1s
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(Thread.currentThread().getName() + "正在执行任务：" + fi);
                });
            }

            System.out.println("主线程没有被阻塞...!");
        } finally {
            pool.shutdown();
        }
    }
}
