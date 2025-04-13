package com.mrgao.java.base.aqs;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

/**
 * @Description 手撕AQS
 * @Author Mr.Gao
 * @Date 2025/4/13 18:31
 */
public class MyLock {

    AtomicBoolean state = new AtomicBoolean(false);

    // 定义一个当前线程ownerThread
    Thread ownerThread;

    /**
     * 队列的头结点
     */
    AtomicReference<Node> head = new AtomicReference<>(new Node());
    /**
     * 队列的尾结点
     */
    AtomicReference<Node> tail = new AtomicReference<>(head.get());

    /**
     * 加锁
     */
    public void lock() {
        // v0版本: 通过while(true)死循环，直到成功才跳出循环
        /**
         * v0版本存在问题:
         * 1、其他线程会一直while(true)，导致CPU资源浪费
         * 2、解锁方法存在，当前线程的锁被其他线程释放的情况。
         */
        // lock_v0(false, true);

        if (state.compareAndSet(false, true)) {
            // 表示当前线程获取锁成功，当前线程指向ownerThread
            System.out.println(Thread.currentThread().getName() + " 直接获取锁成功!");
            ownerThread = Thread.currentThread();
            return;
        }

        // 表示锁竞争失败，将当前线程加入队列的尾部
        Node currentNode = new Node();
        currentNode.thread = Thread.currentThread();// 当前线程

        while (true) {// while(true) 确保 currentTail节点为最新的尾节点
            Node currentTail = tail.get();
            if (tail.compareAndSet(currentTail, currentNode)) {// 确保当前节点 设置到尾部
                // 将当前尾节点 设置为当前节点
                currentNode.pre = currentTail;
                currentTail.next = currentNode;
                System.out.println(Thread.currentThread().getName() + ", 添加至链表的尾节点!");
                break;
            }
        }

        while (true) {
            // Condition
            // Head ---> A ---> B ---> C ---> D ---> null
            if (currentNode.pre == head.get() && state.compareAndSet(false, true)) {
                ownerThread = Thread.currentThread();
                System.out.println(Thread.currentThread().getName() + ", head节点的另一个节点被唤醒了...");
                head.set(currentNode);

                // 断链操作
                currentNode.pre.next = null;
                currentNode.pre = null;
                return;
            }

            LockSupport.park();// 阻塞当前线程
        }

    }


    /**
     * 解锁
     */
    public void unlock() {
        if (Thread.currentThread() != ownerThread) {
            // 避免不是当前锁的拥有者解锁的问题
            throw new IllegalMonitorStateException("当前线程不是锁的拥有者，不能解锁");
        }

        System.out.println(Thread.currentThread().getName() + ", 解锁成功!");
        Node headNode = head.get();
        Node nextNode = headNode.next;
//        ownerThread = null;
        state.set(false);// 将锁释放
        if (nextNode != null) {
            System.out.println(Thread.currentThread().getName() + ", 唤醒下一个节点：" + nextNode.thread.getName());
            LockSupport.unpark(nextNode.thread);
        }

        // lock_v0(true, false);
    }

    /**
     * 加锁
     *
     * @param expectedValue
     * @param newValue
     */
    private void lock_v0(boolean expectedValue, boolean newValue) {
        while (true) {
            if (state.compareAndSet(expectedValue, newValue)) {
                break;
            }
        }
    }

    /**
     * 节点
     */
    class Node {
        private Node pre;
        private Node next;
        private Thread thread;
    }
}
