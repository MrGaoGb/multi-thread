package com.mrgao.likou.array;

import java.util.*;

/**
 * @author Mr.Gao
 * @apiNote:删除有序数组中的重复项
 * @date 2024/12/18 17:19
 */
public class Day03 {

    public static void main(String[] args) {

        int[] nums = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        // 以集合list去重
        //System.out.println(removeDuplicatesByList(nums));
        // 以集合set去重
        System.out.println(removeDuplicatesBySet(nums));

        //printSet();
    }

    /**
     * 删除有序数组中的重复项
     *
     * @param nums
     * @return
     */
    private static int removeDuplicatesByList(int[] nums) {
        List<Integer> list = new ArrayList<>();
        int right = 0;
        for (int i = 0; i < nums.length; i++) {
            if (!list.contains(nums[i])) {
                // 添加
                list.add(nums[i]);
                nums[right] = nums[i];
                right++;
            }
        }
        return list.size();
    }

    /**
     * 根据set集合的方式删除重复元素
     *
     * @param nums
     * @return
     */
    private static int removeDuplicatesBySet(int[] nums) {
        Set<Integer> set = new HashSet<>();
        int right = 0;
        for (int i = 0; i < nums.length; i++) {
            if (set.add(nums[i])) {
                nums[right] = nums[i];
                right++;
            }
        }
        return set.size();
    }


    public static void printSet() {
        Set<Integer> set = new HashSet<>();
        System.out.println(set.add(1));
        System.out.println(set.add(1));
        System.out.println(set.add(2));

        System.out.println(Arrays.toString(set.toArray()));
    }
}
