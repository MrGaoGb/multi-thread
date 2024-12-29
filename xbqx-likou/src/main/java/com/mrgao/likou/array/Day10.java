package com.mrgao.likou.array;

/**
 * @Description 移除元素
 * @Author Mr.Gao
 * @Date 2024/12/30 0:21
 */
public class Day10 {

    public static void main(String[] args) {
//        int[] nums = {3, 2, 2, 3};
//        System.out.println(removeElement(nums, 3));

        int[] nums2 = {0, 1, 2, 2, 3, 0, 4, 2};
        System.out.println(removeElement(nums2, 2));
    }

    public static int removeElement(int[] nums, int val) {
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                // 表示元素不删除
                nums[index++] = nums[i];
            }
        }
        return index;
    }

}
