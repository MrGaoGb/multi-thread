package com.mrgao.thread.test.listener;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Description TODO
 * @Author Mr.Gao
 * @Date 2024/9/6 0:11
 */
@Component
public class MyApplicationListener implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        System.out.println("=============自定义MyApplicationListener,监听ApplicationStartedEvent=============Start!");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("=============自定义MyApplicationListener,监听ApplicationStartedEvent=============End!");
    }
}
