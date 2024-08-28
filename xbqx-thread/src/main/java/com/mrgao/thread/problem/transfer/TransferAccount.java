package com.mrgao.thread.problem.transfer;

import lombok.*;

/**
 * @Description TODO
 * @Author Mr.Gao
 * @Date 2024/8/29 0:56
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferAccount {

    private Integer balance;

    private Object lock;

    public TransferAccount(Object lock, Integer balance ) {
        this.lock = lock;
        this.balance = balance;
    }

    /**
     * synchronized表示锁定的是当前对象,所以跟target不是同一把锁，所以会输出以下结果：
     * <p>
     * ====================输出A余额(转账后):100
     * ====================输出B余额(转账后):300
     * ====================输出C余额(转账后):200
     * </p>
     * 因此，解决该问题的思路是：需要用同一把锁就可以了
     * @param target
     * @param transferAmount
     */
    public synchronized void transfer(TransferAccount target, Integer transferAmount) {
        if (this.balance >= transferAmount) {
            this.balance -= transferAmount;
            target.balance += transferAmount;
        }
    }

    public void transfer2(TransferAccount target, Integer transferAmount) {
        synchronized (lock) {
            if (this.balance >= transferAmount) {
                this.balance -= transferAmount;
                target.balance += transferAmount;
            }
        }
    }

    /**
     * 升级版：TransferAccount.class，由JVM保证对象是同一个，唯一一个
     * @param target
     * @param transferAmount
     */
    public void transferUpgrade(TransferAccount target, Integer transferAmount) {
        synchronized (TransferAccount.class){
            if (this.balance >= transferAmount) {
                this.balance -= transferAmount;
                target.balance += transferAmount;
            }
        }
    }
}
