package com.mrgao.thread.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Mr.Gao
 * @date 2024/8/16 17:41
 * @apiNote:Semaphore遇到的坑
 * <p>
 *     release在finally引发的大坑
 * </p>
 */
public class SemaphorePitExample {

    public static void main(String[] args) {

        // 引出问题
//        one();

        // 根据实践，代码逻辑实现
        //two();

        // release 引发的大坑
        three();
    }


    /**
     * 引出问题
     */
    public static void one(){
        Semaphore semaphore = new Semaphore(3, true);
        ReentrantLock reentrantLock = new ReentrantLock();
        Thread threadA = new Thread(new MyRunnable(1, semaphore, reentrantLock), "thread-A");
        Thread threadB = new Thread(new MyRunnable(2, semaphore, reentrantLock), "thread-B");
        Thread threadC = new Thread(new MyRunnable(1, semaphore, reentrantLock), "thread-C");
        threadA.start();
        threadB.start();
        threadC.start();

    }

    /**
     * 代码实践
     */
    public static void two(){
        int parkSpace = 3;
        System.out.println("网吧里有" + parkSpace + "台机器,先到先得哦！");
        Semaphore semaphore = new Semaphore(parkSpace, true);
        Thread threadA = new Thread(new InternetBar(1, semaphore), "歪歪");
        Thread threadB = new Thread(new InternetBar(2, semaphore), "刘波儿、龙傲天");
        Thread threadC = new Thread(new InternetBar(1, semaphore), "张伟");
        threadA.start();
        threadB.start();
        threadC.start();
    }

    /**
     * release 使用不当的大坑
     */
    public static void three(){
        int parkSpace = 3;
        System.out.println("网吧里有" + parkSpace + "台机器,先到先得哦！");
        Semaphore semaphore = new Semaphore(parkSpace, true);
        // release操作引发的大坑
//        Thread threadA = new Thread(new InternetNewBar(1, semaphore), "歪歪");
//        Thread threadB = new Thread(new InternetNewBar(2, semaphore), "刘波儿、龙傲天");
//        Thread threadC = new Thread(new InternetNewBar(1, semaphore), "张伟");
        // 优化后的(未抢到许可证时，不执行释放操作)
        Thread threadA = new Thread(new InternetOneBar(1, semaphore), "歪歪");
        Thread threadB = new Thread(new InternetOneBar(2, semaphore), "刘波儿、龙傲天");
        Thread threadC = new Thread(new InternetOneBar(1, semaphore), "张伟");
        threadA.start();
        threadB.start();
        threadC.start();
        //模拟网管劝退
        threadB.interrupt();
    }


}


/**
 *
 */
class MyRunnable implements Runnable {

    private int num;

    private Semaphore semaphore;

    private ReentrantLock lock;

    public MyRunnable(int num, Semaphore semaphore, ReentrantLock lock) {
        this.num = num;
        this.semaphore = semaphore;
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + ",获取信号量的个数:"+num+",准备执行!");
            semaphore.acquire(num);
            //System.out.println("剩余可用许可证: " + semaphore.drainPermits());
            System.out.println("剩余可用许可证: " + semaphore.availablePermits());
            System.out.println(Thread.currentThread().getName() + "执行完成。。。。");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release(num);
        }
    }
}

/**
 * 举个例子
 * 比如，有一个网吧，这个网吧只有三个机子，所以放学的时候我们要去抢位置。
 *
 * 抢位置的同学，分别是学渣歪歪，学霸张伟，以及刘波儿、龙傲天这一对好基友。
 *
 * 假设，学渣歪歪是最先抢到一个机位的。
 *
 * 那么请问网吧里面还有几个位置呢？
 *
 * 对咯~
 *
 * 还有 2 个位置。
 *
 * 刘波儿、龙傲天到了后发现，刚好还剩下 2 台机子，于是好基友手拉手，一起愉快的占用了这 2 个位置。
 *
 * 那么问题又来了，请问网吧这时候还有几个位置呢？
 *
 * 又对咯~
 *
 * 满员了，没有位置了。
 *
 * 没多久，学霸张伟到了，发现没有机子了，怎么办呢？
 *
 * 只有在网吧门口等一下了。
 *
 * 没一会，歪歪的网费就用完了，下机走人。
 *
 * 张伟一屁股就坐在了位置上，开始愉快的网上冲浪了。
 *
 * 这个时候网吧还是没有空位的。
 */
class InternetBar implements Runnable{
    private final int n;
    private final Semaphore semaphore;

    public InternetBar(int n, Semaphore semaphore) {
        this.n = n;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            if (semaphore.availablePermits() < n) {
                System.out.println(Thread.currentThread().getName() + "来上网了,但是网吧位置不够了,等着吧");
            }
            semaphore.acquire(n);
            System.out.println(Thread.currentThread().getName() + "占到位置咯,剩余位置:" + semaphore.availablePermits() + "台");
            //模拟上网时长
            int internetBarTime = ThreadLocalRandom.current().nextInt(1, 6);
            TimeUnit.SECONDS.sleep(internetBarTime);
            System.out.println(Thread.currentThread().getName() + "的网费没了,上了" + internetBarTime + "小时网");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            semaphore.release(n);
            System.out.println(Thread.currentThread().getName() + "走后,剩余位置:" + semaphore.availablePermits() + "台");
        }
    }
}

/**
 * release引发的大坑
 */
class InternetNewBar implements Runnable{
    private final int n;
    private final Semaphore semaphore;

    public InternetNewBar(int n, Semaphore semaphore) {
        this.n = n;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            if (semaphore.availablePermits() < n) {
                System.out.println(Thread.currentThread().getName() + "来上网了,但是网吧位置不够了,等着吧");
            }
            semaphore.acquire(n);
            System.out.println(Thread.currentThread().getName() + "占到位置咯,剩余位置:" + semaphore.availablePermits() + "台");
            //模拟上网时长
            int internetBarTime = ThreadLocalRandom.current().nextInt(1, 6);
            TimeUnit.SECONDS.sleep(internetBarTime);
            System.out.println(Thread.currentThread().getName() + "的网费没了,上了" + internetBarTime + "小时网");
        } catch (Exception e) {
            System.err.println(Thread.currentThread().getName() + "被网管劝走了。");
        } finally {
            semaphore.release(n);
            System.out.println(Thread.currentThread().getName() + "走后,剩余位置:" + semaphore.availablePermits() + "台");
        }
    }
}

class InternetOneBar implements Runnable{
    private final int n;
    private final Semaphore semaphore;

    public InternetOneBar(int n, Semaphore semaphore) {
        this.n = n;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            if (semaphore.availablePermits() < n) {
                System.out.println(Thread.currentThread().getName() + "来上网了,但是网吧位置不够了,等着吧");
            }
            semaphore.acquire(n);
            System.out.println(Thread.currentThread().getName() + "占到位置咯,剩余位置:" + semaphore.availablePermits() + "台");
        }catch (InterruptedException e) {
            // 未抢到许可证 不做任何操作，目的是不执行finally操作
            System.err.println(Thread.currentThread().getName() + "被网管劝走了。");
            return;
        }
        try {
            //模拟上网时长
            int internetBarTime = ThreadLocalRandom.current().nextInt(1, 6);
            TimeUnit.SECONDS.sleep(internetBarTime);
            System.out.println(Thread.currentThread().getName() + "的网费没了,上了" + internetBarTime + "小时网");
        } catch (Exception e) {
            System.err.println(Thread.currentThread().getName() + "被网管劝走了。");
        } finally {
            semaphore.release(n);
            System.out.println(Thread.currentThread().getName() + "走后,剩余位置:" + semaphore.availablePermits() + "台");
        }
    }
}