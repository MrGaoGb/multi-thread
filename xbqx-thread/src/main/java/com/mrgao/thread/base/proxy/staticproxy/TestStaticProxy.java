package com.mrgao.thread.base.proxy.staticproxy;

import com.mrgao.thread.base.proxy.Ceo;

/**
 * @Description 单元测试静态代理
 * @Author Mr.Gao
 * @Date 2024/9/24 23:27
 */
public class TestStaticProxy {
    public static void main(String[] args) {
        // 创建一个包装类
        CeoStaticProxy staticProxy = new CeoStaticProxy(new Ceo());
        // 开始执行
        staticProxy.meeting();
    }
}
