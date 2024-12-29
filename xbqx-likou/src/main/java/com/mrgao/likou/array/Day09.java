package com.mrgao.likou.array;

/**
 * @Description 跳跃游戏
 * @Author Mr.Gao
 * @Date 2024/12/29 23:40
 */
public class Day09 {

    public static void main(String[] args) {
//        int[] nums = {2, 3, 1, 1, 4};
        int[] nums = {3, 2, 1, 0, 4};
        System.out.println(canJump(nums));
    }

    /**
     * 跳跃游戏
     *
     * @param nums
     * @return
     */
    public static boolean canJump(int[] nums) {
        int n = nums.length;
        int reach = 0;// 最大跳跃距离
        for (int i = 0; i < n; ++i) {
            if (i > reach) {
                return false;
            }
            reach = Math.max(reach, i + nums[i]);
        }
        return true;
    }

}
