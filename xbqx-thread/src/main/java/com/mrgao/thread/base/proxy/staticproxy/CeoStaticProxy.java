package com.mrgao.thread.base.proxy.staticproxy;

import com.mrgao.thread.base.proxy.ICEO;

/**
 * @Description 静态代理
 * @Author Mr.Gao
 * @Date 2024/9/24 23:23
 */
public class CeoStaticProxy implements ICEO {

    private ICEO iceo;

    public CeoStaticProxy(ICEO iceo) {
        this.iceo = iceo;
    }

    @Override
    public void meeting() {
        System.out.println("##增强处理(before),先接待客人，然后通知老板开会!");
        iceo.meeting();
        System.out.println("##增强处理(after),老板开完会了，准备送客~~~");
    }
}
