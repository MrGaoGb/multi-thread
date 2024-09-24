package com.mrgao.thread.base.proxy.dynamicproxy.cglib;

import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.cglib.proxy.*;

import java.lang.reflect.Method;

/**
 * @Description cglib动态代理实现
 * @Author Mr.Gao
 * @Date 2024/9/24 23:45
 */
public class CglibProxyFactory {

    /**
     * 创建cglib动态代理类
     *
     * @param clazz
     * @return
     */
    public static Object getProxy(Class<?> clazz) {
        //指定文件夹生成class文件
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D:\\Project\\xbqx\\multi-thread\\");
        // 创建动态代理增强类
        Enhancer enhancer = new Enhancer();
        // 设置类加载器
        enhancer.setClassLoader(clazz.getClassLoader());
        // 设置被代理类
        enhancer.setSuperclass(clazz);
        // 设置方法拦截器
        enhancer.setCallbacks(new Callback[]{
                new MethodInterceptor() {
                    @Override
                    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                        System.out.println("##增强处理(before),先接待客人，然后通知老板开会!");
                        // 执行目标方法
                        Object invokeSuper = methodProxy.invokeSuper(o, objects);
                        System.out.println("##增强处理(after),老板开完会了，准备送客~~~");
                        return invokeSuper;
                    }
                },
                new MethodInterceptor() {
                    @Override
                    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                        Object invokeSuper = methodProxy.invokeSuper(o, objects);
                        return invokeSuper;
                    }
                }
        });
        // 对不同方法设置不同的回调逻辑，CallbackFilter中return值为回调数组Callback[]的下标
        enhancer.setCallbackFilter(new CallbackFilter() {
            @Override
            public int accept(Method method) {
                //System.out.println("执行方法名称:" + method.getName());
                if (method.getName().equals("meeting")) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
        // 创建代理类
        return enhancer.create();
    }

}
