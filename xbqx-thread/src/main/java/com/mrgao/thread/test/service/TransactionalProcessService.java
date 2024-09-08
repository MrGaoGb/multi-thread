package com.mrgao.thread.test.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @Description 事务注入
 * @Author Mr.Gao
 * @Date 2024/9/7 15:15
 */
@Slf4j
@Service
public class TransactionalProcessService {


    /**
     * 根据事务注解@Transactional实现用户信息保存
     */
    @Transactional
    public void saveUser() {
        log.info("==保存用户信息 请求时间:{}!", LocalDateTime.now());
        try {
            // 模拟数据库数据保存逻辑
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("==用户报错完成 当前时间:{}!", LocalDateTime.now());
    }
}
