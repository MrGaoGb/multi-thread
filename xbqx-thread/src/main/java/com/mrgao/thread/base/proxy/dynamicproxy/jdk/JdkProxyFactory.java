package com.mrgao.thread.base.proxy.dynamicproxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Description JDK动态代理
 * @Author Mr.Gao
 * @Date 2024/9/24 23:29
 */
public class JdkProxyFactory {
    /**
     * JDK动态代理
     *
     * @param target
     * @return
     */
    public static Object getProxy(Object target) {
        // 保存生成的代理类的字节码文件: jdk8 下的配置，默认生成的路径是项目根目录
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("##增强处理(before),先接待客人，然后通知老板开会!");
                        // 执行目标方法
                        Object invoke = method.invoke(target, args);
                        System.out.println("##增强处理(after),老板开完会了，准备送客~~~");
                        return invoke;
                    }
                }
        );
    }
}
