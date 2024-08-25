package com.mrgao.thread.problem;

/**
 * @Description 并发编程幕后黑手-线程可见性问题
 * @Author Mr.Gao
 * @Date 2024/8/23 23:37
 * <p>
 * 可见性：就是说一个线程对共享变量的修改，另一个线程能够立刻看到
 * </p>
 */
public class ThreadVisibilityDemo {

    private long count = 0;

    //对count的值累加1000次
    private void addCount() {
        for (int i = 0; i < 5000; i++) {
            count++;
        }
    }

    /**
     * 由于多核CPU的缘故，因此count值
     *
     * @return
     * @throws InterruptedException
     */
    public long execute() throws InterruptedException {
        //创建两个线程，执行count的累加操作
        Thread threadA = new Thread(() -> {
            addCount();
        });
        Thread threadB = new Thread(() -> {
            addCount();
        });
        //启动线程
        threadA.start();
        threadB.start();
        //等待线程执行结束
        threadA.join();
        threadB.join();
        //返回结果
        return count;
    }

    /**
     * 输出结果如下：
     * 理想结果：应该是 10000 = 5000(线程A) + 5000(线程B)
     * <p>
     * 实际输出结果如下：
     * 9716
     * 9592
     * 8921
     * 9978
     * 6193
     * 7170
     * </p>
     * <p>
     * 我们一起来分析下这种情况：假设线程A和线程B同时执行，第一次都会将count = 0读取到各自的CPU缓存中，执行完count++之
     * 后，各自CPU缓存中的count的值为1。线程A和线程B同时将count写入内存后，内存中的count值为1，而不是2。在整个过程中，
     * 线程A和线程B都是基于各自CPU中缓存的count的值来进行计算的，最终会导致count的值小于或者等于2000。这就是缓存导致的可
     * 见性问题。
     * <p>
     * 实际上，如果将上述的代码由循环1000次修改为循环1亿次，你会发现最终count的值会接近于1亿，而不是2亿。如果只是循环
     * 1000次，count的值就会接近于2000。不信，你自己可以运行尝试。造成这种结果的原因就是两个线程不是同时启动的，中间存在
     * 一个时间差。
     * </p>
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        ThreadVisibilityDemo demo = new ThreadVisibilityDemo();
        long count = demo.execute();
        // 输出结果
        System.out.println(count);
    }

}
