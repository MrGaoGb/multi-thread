package com.mrgao.thread.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description TODO
 * @Author Mr.Gao
 * @Date 2025/3/30 23:47
 */
public class TryFinallyDemo {

    private static Logger logger = LoggerFactory.getLogger(TryFinallyDemo.class);

    public static void main(String[] args) {
        try {

            int a = 1;

            int b = 2;

            int c = b / 2;

            // 终止JVM运行(如果使用System.exit(0);则finally中不会被执行)
            //System.exit(0);

        } catch (Exception ex) {
            logger.info("catch....!");
        } finally {
            logger.info("finally.....!");
        }
    }
}
