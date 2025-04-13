package com.mrgao.java.base.scheduled;

/**
 * @Description TODO
 * @Author Mr.Gao
 * @Date 2025/4/13 17:30
 */

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Job implements Comparable<Job> {

    // 待执行的任务
    private Runnable task;

    // 任务执行的开始时间
    private long startTime;

    // 延时时间
    private long delay;

    @Override
    public int compareTo(Job o) {
        return Long.compare(this.startTime, o.startTime);
    }
}
