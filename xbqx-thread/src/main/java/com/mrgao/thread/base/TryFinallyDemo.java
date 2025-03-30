package com.mrgao.thread.base;

/**
 * @Description TODO
 * @Author Mr.Gao
 * @Date 2025/3/30 23:47
 */
public class TryFinallyDemo {
    public static void main(String[] args) {
        try {
            System.out.println("try");
            System.out.println("##########业务逻辑(Begin)############");
            System.out.println("......");
            System.out.println("##########业务逻辑(Begin)############");

            // 终止JVM运行
            //System.exit(0);

        } finally {
            System.out.println("finally");
        }
    }
}
