package com.mrgao.thread;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

//@EnableAsync //开启异步线程
@SpringBootApplication
public class XbqxThreadApplication {

    public static void main(String[] args) {
        SpringApplication.run(XbqxThreadApplication.class, args);
    }

}
