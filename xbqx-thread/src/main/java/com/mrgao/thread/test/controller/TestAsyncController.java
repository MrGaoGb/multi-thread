package com.mrgao.thread.test.controller;

import com.mrgao.thread.test.service.AsyncProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Author Mr.Gao
 * @Date 2024/9/4 22:07
 */
@Slf4j
@RestController
public class TestAsyncController {

    @Autowired
    private AsyncProcessService asyncProcessService;

    /**
     * 异步给手机号发送短信
     *
     * @param phoneNum
     * @return
     */
    @GetMapping("/testAsync/sendSmsCode")
    public String sendSmsCode(String phoneNum) {
        log.info("线程:{}, 为手机号发送短信:{}!", Thread.currentThread().getName(), phoneNum);
        asyncProcessService.sendSmsCode(phoneNum);
        log.info("线程:{}, 为手机号发送短信:{}!", Thread.currentThread().getName(), phoneNum);
        return "success";
    }

}
