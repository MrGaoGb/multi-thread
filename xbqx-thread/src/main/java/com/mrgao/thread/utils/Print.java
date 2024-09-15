package com.mrgao.thread.utils;

/**
 * @Description TODO
 * @Author Mr.Gao
 * @Date 2024/9/15 22:16
 */
public class Print {

    /**
     * 在正式输出的内容前，输出线程的名称
     *
     * @param s 待输出的字符串
     */
    public static void tco(Object s) {
        String cft = "[" + Thread.currentThread().getName() + "]" + "：" + s;
        //提交线程池进行异步输出，使得输出过程不影响当前线程的执行
        //异步输出的好处：不会造成输出乱序，也不会造成当前线程阻塞
//        ThreadUtil.execute(() ->
//        {
//            synchronized (System.out)
//            {
//                System.out.println(cft);
//            }
//        });

        synchronized (System.out) {
            System.out.println(cft);
        }
    }
}
