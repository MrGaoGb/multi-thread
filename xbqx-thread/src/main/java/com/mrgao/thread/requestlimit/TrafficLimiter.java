package com.mrgao.thread.requestlimit;

/**
 * 限流算法
 */
public interface TrafficLimiter {

    public boolean limit();

}
