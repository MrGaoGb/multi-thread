package com.mrgao.thread.base;

import java.util.Arrays;

/**
 * @author Mr.Gao
 * @apiNote:
 * @date 2024/9/13 11:20
 */
public class TestReturnForEach {

    public static void main(String[] args) {

        // 通过return终止循环
        //forEachElementsByReturn();

        // 通过break终止循环
        //forEachElementsByBreak();

        // 通过lambda表达式的return终止循环
        // 此处return相当于continue操作 还会进行下一次执行;并且lambda中不支持break
        lambdaForEachElementsByReturn();
    }

    /**
     * 遍历元素
     */
    public static void forEachElementsByReturn() {
        for (int i = 0; i < 100; i++) {
            if (i == 99) {
                System.out.println("i=99!");
                return;
            }
        }
        System.out.println("do other things！");
    }


    /**
     * 遍历元素
     */
    public static void forEachElementsByBreak() {
        for (int i = 0; i < 100; i++) {
            if (i == 99) {
                System.out.println("i=99!");
                break;
            }
        }
        System.out.println("do other things！");
    }


    /**
     * 遍历元素
     * 此处return相当于continue操作 还会进行下一次执行;并且lambda中不支持break和continue
     */
    public static void lambdaForEachElementsByReturn() {
        int max = 6;
        int[] strArr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Arrays.stream(strArr).forEach(num -> {
            if (num == max) {
                System.out.println("=======最大值i=" + num);
                return;
            }
            System.out.println("当前值i=" + num);
        });
        System.out.println("do other things！");
    }

}
