package com.mrgao.thread;

import com.mrgao.thread.problem.transfer.SynchronizedTransferAccountDemo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class XbqxThreadApplicationTests {

    @Test
    void contextLoads() {
    }


    /**
     * 案例描述：synchronized引发的诡异转账问题
     */
    @Test
    public void test() {
        SynchronizedTransferAccountDemo transferAccountDemo = new SynchronizedTransferAccountDemo();
        for (int i = 0; i < 50000; i++) {
            // 账户A、B、C账户余额分别为200, A给B转账100 然后B给C转账100，最后输出各自余额还剩多少

            // 第一种引出问题
            boolean execResult = transferAccountDemo.planOne();

            // 使用同一把锁锁定待操作的资源
            //boolean execResult = transferAccountDemo.planTwo();

            // 使用同一把锁锁定待操作的资源
            //boolean execResult = transferAccountDemo.planThree();


            if (execResult) {
                break;
            }
        }
    }

}
