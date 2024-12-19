package com.mrgao.likou.array;

import java.util.Arrays;

/**
 * @author Mr.Gao
 * @apiNote:删除有序数组中的重复项 II
 * @date 2024/12/18 18:08
 */
public class Day04 {

    public static void main(String[] args) {
        int[] nums = {1, 1, 1, 2, 2, 3};
        //int[] nums = {0, 0, 1, 1, 1, 1, 2, 3, 3};
        //System.out.println(removeDuplicates(nums));
        System.out.println(removeDuplicatesV1(nums));
    }

    /**
     * 删除有序数组中的重复项 II(提交未通道)
     * <p>
     * 该方法主要是筛选重复的元素，跟Day03.java中的方法类似，只是多了一个计数器，
     * </p>
     *
     * @param nums
     * @return
     */
    private static int removeDuplicates(int[] nums) {
        // 数组中没有元素
        if (nums.length == 0) {
            return 0;
        }
        int left = 1;
        // 遍历数组
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] != nums[i + 1]) {
                // 表示
                nums[left] = nums[i + 1];
                left++;
            }
        }
        return left;
    }

    /**
     * 删除有序数组中的重复项 II（提交版）
     *
     * @param nums
     * @return
     */
    private static int removeDuplicatesV1(int[] nums) {
        // 数组中没有元素
        if (nums.length == 0) {
            return 0;
        }
        // 此处表示必定存在元素，所以从1开始
        int left = 1;
        int count = 0; // 记录相同元素出现的次数
        // 遍历数组
        // {1, 1, 1, 2, 2, 3}
        // 0: 1, 1  count = 1, left = 2(下标值后移)
        // 1: 1, 1  count = 1, left = 2(下标值后移)
        // 2: 1, 2  count = 0, left = 3(下标值后移)
        // 3: 2, 2  count = 1, left = 4(下标值后移)
        // 4: 2, 3  count = 0, left = 5(下标值后移)
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] != nums[i + 1]) {
                // 表示
                nums[left] = nums[i + 1];
                left++;
                count = 0;// 重置次数
            } else {
                // 两个数相等
                if (count != 1) {
                    count++;
                    if (nums[left - 1] == nums[i + 1]) {
                        nums[left] = nums[i + 1];
                    }
                    left++;
                }
            }
        }
        System.out.println(Arrays.toString(nums));
        return left;
    }
}
