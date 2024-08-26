package com.mrgao.thread.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @Description TODO
 * @Author Mr.Gao
 * @Date 2024/8/26 23:56
 */
public class ForkJoinTaskDemo extends RecursiveTask<Integer> {

    public static final int threshold = 2;
    private int start;
    private int end;
    public ForkJoinTaskDemo(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        //如果任务足够小就计算任务
        boolean canCompute = (end - start) <= threshold;
        if (canCompute) {
            for (int i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            // 如果任务大于阈值，就分裂成两个子任务计算
            int middle = (start + end) / 2;
            ForkJoinTaskDemo leftTask = new ForkJoinTaskDemo(start, middle);
            ForkJoinTaskDemo rightTask = new ForkJoinTaskDemo(middle + 1, end);
            // 执行子任务
            leftTask.fork();
            rightTask.fork();
            // 等待任务执行结束合并其结果
            int leftResult = leftTask.join();
            int rightResult = rightTask.join();
            // 合并子任务
            sum = leftResult + rightResult;
        }
        return sum;
    }

    public static void main(String[] args) {
        // 创建ForkJoinPool线程池
        ForkJoinPool pool = new ForkJoinPool();
        // 创建ForkJoinTask
        ForkJoinTaskDemo task = new ForkJoinTaskDemo(1, 100);

        // 创建一个任务提交至线程池pool中
        ForkJoinTask<Integer> forkJoinTask = pool.submit(task);

        // 获取结果
        Integer result = forkJoinTask.join();
        System.out.println("得到结果:"+result);
    }
}
