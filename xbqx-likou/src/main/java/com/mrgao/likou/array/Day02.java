package com.mrgao.likou.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mr.Gao
 * @apiNote:
 * @date 2024/12/16 15:10
 */
public class Day02 {

    public static void main(String[] args) {
        int[] nums = {3, 2, 2, 3};
//        int[] nums = {0, 1, 2, 2, 3, 0, 4, 2};
        //System.out.println(subArraySum(nums, 3));
//        System.out.println(subArraySumV2(nums, 2));
//        System.out.println(subArraySumV3(nums, 3));
//        System.out.println(subArraySumV4(nums, 3));
        System.out.println(removeElement(nums, 3));
        System.out.println(Arrays.toString(nums));
    }

    public static int subArraySum(int[] nums, int val) {
        int count = 0;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == val) {
                count++;
            } else {
                list.add(nums[i]);
            }
        }
        nums = list.stream().mapToInt(Integer::intValue).toArray();
        System.out.println(Arrays.toString(nums));
        return count;
    }

    public static int subArraySumV2(int[] nums, int val) {
        int count = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] == val) {
                int nextIndex = i + 1;
                if (nextIndex <= nums.length - 1) {
                    // 找到下一个元素 且不等于 val
                    boolean flag = false;
                    while (true) {
                        if (nextIndex > nums.length - 1) {
                            break;
                        }
                        if (nums[nextIndex] != val) {
                            flag = true;
                            break;
                        }
                        nextIndex++;
                    }

                    if (flag) {
                        // 元素交换
                        int temp = nums[i];
                        nums[i] = nums[nextIndex];
                        nums[nextIndex] = temp;
                    }
                }
            } else {
                count++;
            }
        }
        System.out.println(Arrays.toString(nums));
        return count;
    }

    public static int subArraySumV3(int[] nums, int val) {
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == val) {
                int nextIndex = nums.length - 1;
                if (nextIndex > i) {
                    // 找到下一个元素 且不等于 val
                    boolean flag = false;
                    while (true) {
                        if (nextIndex <= i) {
                            break;
                        }
                        if (nums[nextIndex] != val) {
                            flag = true;
                            break;
                        }
                        nextIndex--;
                    }

                    if (flag) {
                        // 元素交换
                        int temp = nums[i];
                        nums[i] = nums[nextIndex];
                        nums[nextIndex] = temp;

                    }
                }
            } else {
                count++;
            }
        }
        System.out.println(Arrays.toString(nums));
        return count;
    }


    public static int subArraySumV4(int[] nums, int val) {
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                count++;
            } else {
                int tailIndex = nums.length - 1;
                while (tailIndex > i) {
                    if (nums[tailIndex] != val) {
                        int temp = nums[i];
                        nums[i] = nums[tailIndex];
                        nums[tailIndex] = temp;
                        break;
                    }
                    tailIndex--;
                }
            }
        }
        return count;
    }

    /**
     * 官方题解
     *
     * @param nums
     * @param val
     * @return
     */
    public static int removeElement(int[] nums, int val) {
        int n = nums.length;
        int left = 0;
        for (int right = 0; right < n; right++) {
            if (nums[right] != val) {
                nums[left] = nums[right];
                left++;
            }
        }
        return left;
    }
}
