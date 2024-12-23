package com.mrgao.likou.array;

import com.alibaba.fastjson.JSON;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 多数元素
 * @Author Mr.Gao
 * @Date 2024/12/23 23:02
 */
public class Day05 {

    public static void main(String[] args) {

//        int[] nums = {3, 2, 3};
        int[] nums = {2, 2, 1, 1, 1, 2, 2};
//        System.out.println(majorityElement(nums));

        System.out.println(majorityElementV1(nums));
    }

    /**
     * 给定一个大小为 n 的数组 nums ，返回其中的多数元素。多数元素是指在数组中出现次数 大于 ⌊ n/2 ⌋ 的元素。
     * <p>
     * 你可以假设数组是非空的，并且给定的数组总是存在多数元素。
     * 示例 1：
     * <p>
     * 输入：nums = [3,2,3]
     * 输出：3
     * 示例 2：
     * <p>
     * 输入：nums = [2,2,1,1,1,2,2]
     * 输出：2
     *
     * @param nums
     * @return
     */
    public static int majorityElement(int[] nums) {
        // 仅存在一个元素
        if (nums.length == 1) {
            return nums[0];
        }
        // 大小排序
        Arrays.sort(nums);
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])) {
                map.put(nums[i], map.get(nums[i]) + 1);
            } else {
                map.put(nums[i], 1);
            }
        }
        System.out.println(JSON.toJSONString(map));
        // 从map中找出最大的val值所对应的key
        Integer maxCountVal = map.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .get();
        return maxCountVal;
    }

    public static int majorityElementV1(int[] nums) {
        // 仅存在一个元素
        if (nums.length == 1) {
            return nums[0];
        }
        // 大小排序
        Arrays.sort(nums);
        int left = nums[0]; // 用于标记当前的值
        int count = 1; // 用于统计次数
        int maxCount = 1;// 标记最大次数
        int maxCountVal = nums[0];
        // 1,1,2,2,3,3,3
        for (int i = 1; i < nums.length; i++) {
            if (left == nums[i]) {
                count++;
                if (i == nums.length - 1) {
                    if (count > maxCount) {
                        maxCountVal = nums[i];
                    }
                }
            } else {
                // 元素不相等时，开始累计下一个元素
                left = nums[i];
                if (count > maxCount) {
                    maxCountVal = nums[i - 1];
                    maxCount = count;// 记录当前最大值
                }
                // 重置计数
                count = 1;
            }
        }

        return maxCountVal;
    }
}
