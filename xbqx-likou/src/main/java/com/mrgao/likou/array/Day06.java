package com.mrgao.likou.array;

import java.util.Arrays;

/**
 * @Description 轮转数组
 * @Author Mr.Gao
 * @Date 2024/12/24 23:08
 */
public class Day06 {

    public static void main(String[] args) {
        /**
         * 向右轮转 1 步: [7,1,2,3,4,5,6]
         * 向右轮转 2 步: [6,7,1,2,3,4,5]
         * 向右轮转 3 步: [5,6,7,1,2,3,4]
         */
//        int[] nums = {1, 2, 3, 4, 5, 6, 7};
//        rotate(nums, 3);

        int[] nums1 = {-1, -100, 3, 99};
        rotateV1(nums1, 2);
    }

    /**
     * 示例 1:
     * <p>
     * 输入: nums = [1,2,3,4,5,6,7], k = 3
     * 输出: [5,6,7,1,2,3,4]
     * 解释:
     * 向右轮转 1 步: [7,1,2,3,4,5,6]
     * 向右轮转 2 步: [6,7,1,2,3,4,5]
     * 向右轮转 3 步: [5,6,7,1,2,3,4]
     * <p>
     * 示例 2:
     * <p>
     * 输入：nums = [-1,-100,3,99], k = 2
     * 输出：[3,99,-1,-100]
     * 解释:
     * 向右轮转 1 步: [99,-1,-100,3]
     * 向右轮转 2 步: [3,99,-1,-100]
     *
     * @param nums
     * @param k
     */
    public static void rotate(int[] nums, int k) {
        for (int i = 0; i < k; i++) {
            // 取最后一位
            int temp = nums[nums.length - 1];
            for (int j = nums.length - 1; j > 0; j--) {
                // 元素后移一步
                nums[j] = nums[j - 1];
            }
            // 将第一个位置设置值
            nums[0] = temp;
            System.out.println("数组:" + Arrays.toString(nums));
        }
    }

    /**
     * 使用额外的数组
     *
     * @param nums
     * @param k
     */
    public static void rotateV1(int[] nums, int k) {
        int len = nums.length;
        // 创建新数组
        int[] temp = new int[len];
        for (int i = 0; i < len; ++i) {
            temp[(i + k) % len] = nums[i];
        }
        // 拷贝数组
        System.arraycopy(temp, 0, nums, 0, len);
    }
}
