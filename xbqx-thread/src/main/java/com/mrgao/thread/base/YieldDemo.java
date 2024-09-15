package com.mrgao.thread.base;

import com.mrgao.thread.utils.Print;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description 线程的yield（让步）操作的作用是让目前正在执行的线程放弃当前的执行，让出CPU的执行权限，使得CPU去执行其他的线程。
 * 从执行状态来说会从 执行状态 变成 就绪状态。
 * <p>
 * 总结：
 * 1)、 yield仅能使一个线程从运行状态转到就绪状态，而不是阻塞状态
 * 2)、 yield不能保证使得当前运行的线程迅速转到就绪状态
 * 3)、 即使完成了迅速切换，系统通过线程调度机制从所有就绪线程中挑选下一个执行线程时，就绪的线程可能被选中，也有可能选不中，其调度过程受到
 * 其他因素的影响(如优先级)的影响。
 * </p>
 * @Author Mr.Gao
 * @Date 2024/9/15 23:46
 */
public class YieldDemo {

    public static final int MAX_TURN = 100; //执行次数
    public static AtomicInteger index = new AtomicInteger(0); //执行编号//记录线程的执行次数
    private static Map<String, AtomicInteger> metric = new HashMap<>();

    //输出线程的执行次数
    private static void printMetric() {
        Print.tco("metric = " + metric);
    }

    static class YieldThread extends Thread {
        static int threadSeqNumber = 1;

        public YieldThread() {
            super("sleepThread-" + threadSeqNumber);
            threadSeqNumber++;
            //将线程加入到执行次数统计map
            metric.put(this.getName(), new AtomicInteger(0));
        }

        public void run() {
            for (int i = 1; i < MAX_TURN && index.get() < MAX_TURN; i++) {
                Print.tco("线程优先级：" + getPriority());
                index.incrementAndGet();
                //统计一次
                metric.get(this.getName()).incrementAndGet();
                if (i % 2 == 0) {
                    //让步：出让执行的权限
                    Thread.yield();
                }
            }
            //输出所有线程的执行次数
            printMetric();
            Print.tco(getName() + " 运行结束.");
        }
    }


    public static void main(String[] args) {
        Thread thread1 = new YieldThread();
        //设置为最高的优先级
        thread1.setPriority(Thread.MAX_PRIORITY);
        Thread thread2 = new YieldThread();
        //设置为最低的优先级
        thread2.setPriority(Thread.MIN_PRIORITY);
        Print.tco("启动线程.");
        thread1.start();
        thread2.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
