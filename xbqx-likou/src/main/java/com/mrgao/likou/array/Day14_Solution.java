package com.mrgao.likou.array;

/**
 * @apiNote 加油站
 * @author: genbaogao@outlook.com
 * @date: 2025/7/16 10:43
 */
public class Day14_Solution {
    public static void main(String[] args) {
        int[] gas = {1, 2, 3, 4, 5};
        //int[] gas = {2, 3, 4};
        //int[] cost = {3, 4, 5, 1, 2};
        int[] cost = {1, 3, 4, 5, 2};
        //int[] cost = {3, 4, 3};
        System.out.println(canCompleteCircuit(gas, cost));

        int mod = 1 % 5;
        int mod2 = 2 % 5;
        int mod3 = 3 % 5;
        int mod4 = 4 % 5;
        int mod5 = 5 % 5;

        System.out.println(
            "取余运算:" + mod + ", mode2:" + mod2 + ", mode3:" + mod3 + ", mode4:" + mod4 + ", mode5:" + mod5);

        System.out.println("-------------------");

        int temp = 1 / 5;
        int temp1 = 2 / 5;
        int temp2 = 3 / 5;
        int temp3 = 4 / 5;
        int temp4 = 5 / 5;
        System.out.println(
            "取整运算:" + temp + ", temp1:" + temp1 + ", temp2:" + temp2 + ", temp3:" + temp3 + ", temp4:" + temp4);
    }

    public static int canCompleteCircuit(int[] gas, int[] cost) {
        int n = gas.length;
        //考虑从每一个点出发
        for (int i = 0; i < n; i++) {
            int j = i;
            int remain = gas[i];
            //当前剩余的油能否到达下一个点
            while (remain - cost[j] >= 0) {
                //减去花费的加上新的点的补给
                remain = remain - cost[j] + gas[(j + 1) % n]; // 由于是个圆,所以此处取模运算
                j = (j + 1) % n;
                //j 回到了 i
                if (j == i) {
                    return i;
                }
            }
        }
        //任何点都不可以
        return -1;
    }

    /**
     * 只要总油量大于等于总耗油量就肯定能跑完一圈，换句话说，油的剩余量如果大于等于0就肯定能跑完一圈，
     * 这么一想这个问题就简单了，那么总耗油量如果小于0，直接返回-1
     *
     * @param gas
     * @param cost
     * @return
     */
    public static int canCompleteCircuit1(int[] gas, int[] cost) {
        int totalNum = 0;
        int curNum = 0;
        int idx = 0;
        for (int i = 0; i < gas.length; i++) {
            curNum += gas[i] - cost[i];
            totalNum += gas[i] - cost[i];

            if (curNum < 0) {
                idx = (i + 1) % gas.length;
                curNum = 0;
            }
        }

        if (totalNum < 0) {return -1;}

        return idx;
    }
}
