package com.mrgao.limit.controller;

import com.mrgao.limit.algorithm.TrafficLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mr.Gao
 * @apiNote:限流控制器
 * @date 2025/2/25 17:59
 */
@RestController
@RequestMapping("/limit")
public class LimitController {

    @Autowired
    private TrafficLimiter trafficLimiter;

    /**
     * 发送请求
     *
     * @return
     */
    @GetMapping("/sendRequest")
    public String sendRequest() {
        if (trafficLimiter.limit()) {
            return "限流了";
        }
        return "success";
    }

}
