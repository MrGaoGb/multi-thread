package com.mrgao.likou.array;

/**
 * @Description 买卖股票的最佳时机
 * @Author Mr.Gao
 * @Date 2024/12/25 22:49
 */
public class Day07 {

    public static void main(String[] args) {
//        int[] nums = {7, 1, 5, 3, 6, 4};
        int[] nums = {1, 2};
//        System.out.println(maxProfit(nums));
        System.out.println(maxProfitV1(nums));
    }

    /**
     * 最大收益计算
     *
     * @param prices
     * @return
     */
    public static int maxProfit(int[] prices) {
        int maxProfit = 0;// 最大收益值
        for (int i = 0; i < prices.length - 1; i++) {
            int minPrice = prices[i];
            if (minPrice < prices[i + 1]) {
                int index = i;
                while (index < prices.length - 1) {
                    if (prices[index + 1] < minPrice) {
                        // 跳出循环
                        break;
                    }
                    if (prices[index + 1] - minPrice > maxProfit) {
                        // 记录最大收益
                        maxProfit = prices[index + 1] - minPrice;
                    }
                    index++; // 标记下一个元素
                }
            }
        }
        // 返回最大利润
        return maxProfit;
    }


    /**
     * 最大收益计算
     *
     * @param prices
     * @return
     */
    public static int maxProfitV1(int[] prices) {
        int maxProfit = 0;
        int minPrice = Integer.MAX_VALUE;
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < minPrice) {
                minPrice = prices[i];
            } else if (prices[i] - minPrice > maxProfit) {
                maxProfit = prices[i] - minPrice;
            }
        }
        return maxProfit;
    }

}
