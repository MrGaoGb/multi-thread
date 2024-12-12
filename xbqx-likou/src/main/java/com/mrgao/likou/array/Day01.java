package com.mrgao.likou.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @Description 合并两个有序数组
 * @Author Mr.Gao
 * @Date 2024/12/12 23:10
 */
public class Day01 {

    public static void main(String[] args) {
        int[] nums1 = {1, 2, 3, 0, 0, 0};
        int[] nums2 = {2, 5, 6};
//        mergeV1(nums1, 3, nums2, 3);

//        mergeV2(nums1, 3, nums2, 3);

        mergeV3();
    }


    /**
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    public static void mergeV1(int[] nums1, int m, int[] nums2, int n) {
        // 元素总个数
        int len = m + n;
        List<Integer> list = new ArrayList<>();
        // 循环取出所有非0元素
        for (int i = 0; i < len; i++) {
            if (i < nums1.length && nums1[i] != 0) {
                list.add(nums1[i]);
            }
            if (i < nums2.length && nums2[i] != 0) {
                list.add(nums2[i]);
            }
        }
        // 排序
        list.sort(Integer::compare);
        System.out.println(list);
        // 转化为nums1数组
        // list 转化为int数组
        nums1 = list.stream().mapToInt(Integer::intValue).toArray();
//        System.out.println(Arrays.toString(nums1));

    }

    public static void mergeV2(int[] nums1, int m, int[] nums2, int n) {
        for (int i = 0; i < n; i++) {
            nums1[m + i] = nums2[i];
        }
        Arrays.sort(nums1);
        System.out.println(Arrays.toString(nums1));
    }

    public static void mergeV3() {

        int sum = IntStream.of(1, 2, 3, 4,5)
                .filter(e -> e > 2)
                .peek(e -> System.out.println("Filtered value: " + e))
                .map(e -> e * e)
                .peek(e -> System.out.println("Mapped value: " + e))
                .sum();
        System.out.println(sum);
    }
}
