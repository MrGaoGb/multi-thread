package com.mrgao.thread.base.proxy.dynamicproxy.jdk;

import com.mrgao.thread.base.proxy.Ceo;
import com.mrgao.thread.base.proxy.ICEO;

/**
 * @Description 单元测试JDK动态代理
 * @Author Mr.Gao
 * @Date 2024/9/24 23:34
 */
public class TestJdkProxy {
    public static void main(String[] args) {
        // 创建目标类
        Ceo target = new Ceo();
        // 创建目标代理类
        ICEO ceoProxy = (ICEO) JdkProxyFactory.getProxy(target);
        // 执行目标代理类
        ceoProxy.meeting();
    }
}
