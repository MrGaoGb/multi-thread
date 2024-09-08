package com.mrgao.thread.test.service;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * @Description 异步处理Service
 * @Author Mr.Gao
 * @Date 2024/9/4 22:09
 */
@Slf4j
@Service
public class AsyncProcessService {

    /**
     * 异步发送短信
     *
     * @param phoneNum
     */
    @Async // 开启异步
    @Transactional(rollbackFor = Exception.class)
    public void sendSmsCode(String phoneNum) {
        log.info("线程:{},为手机号:{} 发送短信 Begin!", Thread.currentThread().getName(), phoneNum);
        try {
            // 模拟业务逻辑执行
            TimeUnit.SECONDS.sleep(1);
            String smsCode = RandomUtil.randomNumbers(6);
            log.info("线程:{}, 为手机号发送的短信验证码为:{}!", Thread.currentThread().getName(), smsCode);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("线程:{} 发送短信 End!", Thread.currentThread().getName());
    }

}
