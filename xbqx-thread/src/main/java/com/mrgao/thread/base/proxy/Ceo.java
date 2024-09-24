package com.mrgao.thread.base.proxy;

/**
 * @Description 实现类
 * @Author Mr.Gao
 * @Date 2024/9/24 23:22
 */
public class Ceo implements ICEO {
    @Override
    public void meeting() {
        System.out.println("老板需要进行开会...");
    }
}
