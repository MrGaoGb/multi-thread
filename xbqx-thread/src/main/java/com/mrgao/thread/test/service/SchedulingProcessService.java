package com.mrgao.thread.test.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * @author Mr.Gao
 * @apiNote:
 * @date 2024/9/5 16:51
 */
@Slf4j
@Service
public class SchedulingProcessService {

    // 时间格式
    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 定时执行：每间隔五秒执行一次
     */
    @Scheduled(cron = "*/10 * * * * ?") // 通过cron表达式方式：每5秒执行一次
    //@Scheduled(fixedDelay = 10000) // fixedDelay或fixedDelayString: 上一次执行完毕时间点之后多长时间再执行
    //@Scheduled(fixedRate = 10000) // fixedRate: 上一次开始执行时间点之后多长时间再执行
    //@Scheduled(initialDelay = 10000, fixedRate = 5000) // initialDelay: 第一次延迟多长时间后再执行,//第一次延迟10秒后执行，之后按fixedRate的规则每5秒执行一次
    public void work() {
        log.info("@Scheduled任务起来开始工作了, 当前时间:{}!", LocalDateTime.now().format(DateTimeFormatter.ofPattern(PATTERN)));
        try {
            // 模拟业务逻辑 处理10s
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("@Scheduled任务处理完成, 结束时间:{}!", LocalDateTime.now().format(DateTimeFormatter.ofPattern(PATTERN)));
    }
}
