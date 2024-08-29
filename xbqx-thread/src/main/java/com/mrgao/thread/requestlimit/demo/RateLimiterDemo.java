package com.mrgao.thread.requestlimit.demo;

import com.google.common.util.concurrent.RateLimiter;

import java.time.LocalDateTime;

/**
 * @Description 令牌桶算法
 * @Author Mr.Gao
 * @Date 2024/8/29 23:41
 */
public class RateLimiterDemo {

    public static void main(String[] args) {
        //每秒钟生成5个令牌
        RateLimiter limiter = RateLimiter.create(5);

        //返回值表示从令牌桶中获取一个令牌所花费的时间，单位是秒
        System.out.println(limiter.acquire(1));
        System.out.println(limiter.acquire(1));
        System.out.println(limiter.acquire(1));
        System.out.println(limiter.acquire(5));
        System.out.println(limiter.acquire(50));
        System.out.println(limiter.acquire(5));
        System.out.println(limiter.acquire(5));
        System.out.println(limiter.acquire(5));
    }
}
