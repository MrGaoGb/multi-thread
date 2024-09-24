package com.mrgao.thread.base.proxy.dynamicproxy.cglib;

import com.mrgao.thread.base.proxy.Ceo;
import com.mrgao.thread.base.proxy.ICEO;

/**
 * @Description cglib动态代理
 * @Author Mr.Gao
 * @Date 2024/9/24 23:54
 */
public class TestCglibProxy {
    public static void main(String[] args) {
        // 创建Cglib代理
        ICEO ceoProxy = (ICEO) CglibProxyFactory.getProxy(Ceo.class);
        // 执行代理方法
        ceoProxy.meeting();
    }
}
