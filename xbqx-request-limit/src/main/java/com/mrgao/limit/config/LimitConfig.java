package com.mrgao.limit.config;

import com.mrgao.limit.algorithm.TrafficLimiter;
import com.mrgao.limit.algorithm.impl.CounterLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mr.Gao
 * @apiNote:
 * @date 2025/2/25 18:03
 */
@Slf4j
@Configuration
public class LimitConfig {

    /**
     * 限流算法之计数器限流(固定时间窗口)
     *
     * @return
     */
    @Bean
    public TrafficLimiter trafficLimiter() {
        log.info("限流算法之计数器限流配置Bean初始化!");
        return new CounterLimiter();
    }

}
