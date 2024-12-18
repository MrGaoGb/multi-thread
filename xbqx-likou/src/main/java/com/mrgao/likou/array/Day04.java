package com.mrgao.likou.array;

/**
 * @author Mr.Gao
 * @apiNote:删除有序数组中的重复项 II
 * @date 2024/12/18 18:08
 */
public class Day04 {

    public static void main(String[] args) {
        int[] nums = {1, 1, 1, 2, 2, 3};
        System.out.println(removeDuplicates(nums));
    }

    /**
     * 删除有序数组中的重复项 II
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
}
