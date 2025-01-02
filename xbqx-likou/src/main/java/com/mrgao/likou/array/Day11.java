package com.mrgao.likou.array;

import java.util.Arrays;

/**
 * @author Mr.Gao
 * @apiNote:H指数
 * @date 2025/1/2 15:23
 */
public class Day11 {

    public static void main(String[] args) {
        //int[] citations = {3, 0, 6, 1, 5};
        int[] citations = {1,3,1};
        System.out.println(hIndex(citations));
    }

    private static int hIndex(int[] citations) {
        // 排序
        Arrays.sort(citations);
        int hIndex = 0;
        for (int i = citations.length - 1; i >= 0; i--) {
            if (citations[i] > hIndex) {
                hIndex++;
            }
        }
        return hIndex;
    }

}
