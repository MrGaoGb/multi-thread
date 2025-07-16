package com.mrgao.likou.array;

import java.util.Arrays;

/**
 * @author Mr.Gao
 * @apiNote:除自身以外数组的乘积
 * @date 2025/1/7 10:23
 */
public class Day13 {

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4};
        //int[] nums = {-1, 1, 0, -3, 3};
        //int[] ints = productExceptSelf(nums);
        int[] ints = productExceptSelfV1(nums);
        System.out.println(Arrays.toString(ints));
    }

    /**
     * 给你一个整数数组 nums，返回 数组 answer ，其中 answer[i] 等于 nums 中除 nums[i] 之外其余各元素的乘积 。
     * <p>
     * 题目数据 保证 数组 nums之中任意元素的全部前缀元素和后缀的乘积都在  32 位 整数范围内。
     * <p>
     * 请 不要使用除法，且在 O(n) 时间复杂度内完成此题。
     *
     * @param nums
     * @return
     */
    private static int[] productExceptSelf(int[] nums) {
        int[] temp = new int[nums.length];
        int count = 1;
        for (int i = 0; i < nums.length; i++) {
            int index = nums.length - 1;
            while (index >= 0) {
                if (index != i) {
                    count *= nums[index];
                }
                index--;
            }
            temp[i] = count;
            count = 1;// 重置count
        }
        return temp;
    }

    private static int[] productExceptSelfV1(int[] nums) {
        int[] temp = new int[nums.length];

        // 构建R数组
        int[] R = new int[nums.length];
        int[] L = new int[nums.length];

        L[0] = 1;
        for (int i = 1; i < nums.length; i++) {
            L[i] = nums[i] * L[i - 1];
        }
        System.out.println(Arrays.toString(L));
        R[nums.length - 1] = 1;
        for (int i = nums.length - 2; i >= 0; i--) {
            R[i] = nums[i] * R[i + 1];
        }
        System.out.println(Arrays.toString(R));
        return temp;
    }
}
