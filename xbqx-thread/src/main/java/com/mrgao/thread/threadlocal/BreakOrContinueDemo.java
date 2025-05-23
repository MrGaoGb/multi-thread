package com.mrgao.thread.threadlocal;

/**
 * 研究break和continue
 * break:终止跳出该循环体
 * continue: 终止跳出本次循环，进入下一个循环
 * <p>
 * 而 break flag(标识符):终止跳出最外层循环体
 * continue flag(标识符):终止跳出本次循环的最外层循环体，进入下一个循环
 */
public class BreakOrContinueDemo {

    public static void main(String[] args) {

        flag:
        for (int i = 0; i < 10; i++) {
            //System.out.println("外部循环:"+i);
            for (int j = 0; j < 10; j++) {
                //System.out.println("内部循环:"+j);
                int sum = i * j;
                if (sum > 50) {
                    System.out.println(i + "*" + j + "=" + sum);
                    break flag;
                    //break;
                    //continue flag;
                }
            }
        }

        // 继续向下执行
        System.out.println("继续向下执行...!");

    }
}
