package com.mrgao.likou.array;

/**
 * @author Mr.Gao
 * @apiNote:买卖股票的最佳时机 II
 * @date 2024/12/26 10:16
 */
public class Day08 {
    public static void main(String[] args) {
        // 案例一:
        int[] prices = {7, 1, 5, 3, 6, 4};
        // 案例二:
        //int[] prices = {1, 2, 3, 4, 5};
        // 案例三:
        //int[] prices = {7, 6, 4, 3, 1};
        // 案例三:
        //int[] prices = {1, 2};
        // 案例四:
        //int[] prices = {2, 1, 4};

        //System.out.println(maxProfit(prices));
        // 优化
        //System.out.println(maxProfitV1(prices));
        // 最优方案
        System.out.println(maxProfitV2(prices));
    }

    /**
     * 122. 买卖股票的最佳时机 II
     * <p>
     * 解题思路：最小买入点是可变的，若元素一直递增，则最大利润就是当前元素减去最小买入点；
     * 若元素是阶段递增，则最大利润就是当前元素减去最小买入点，同时将各个阶段的值利润值累加；
     * </p>
     *
     * @param prices
     * @return
     */
    private static int maxProfit(int[] prices) {
        if (prices.length == 2 && prices[0] < prices[1]) {
            // 仅存在两个元素，直接计算利润值
            return prices[1] - prices[0];
        }
        // 最大利润值
        int maxProfit = 0;
        // 最小买入点
        int minPrice = Integer.MAX_VALUE;
        for (int i = 0; i < prices.length - 1; i++) {
            if (prices[i] < minPrice) {
                // 确定最小买入点
                minPrice = prices[i];
            } else if (prices[i + 1] > prices[i] && (i + 1) == prices.length - 1) {
                // 表示下一个元素比minPrice大且一直到最后一个元素
                maxProfit += prices[i + 1] - minPrice;
            } else if (prices[i + 1] < prices[i]) {
                // 表示prices[i+1]下一个元素比当前prices[i]小
                maxProfit += prices[i] - minPrice; // 累加利润值
                minPrice = prices[i + 1];// 重置最小买入点
            }
        }
        // 返回最大利润值
        return maxProfit;
    }

    private static int maxProfitV1(int[] prices) {
        // 最大利润值
        int maxProfit = 0;
        // 最小买入点
        int minPrice = Integer.MAX_VALUE;
        for (int i = 0; i < prices.length - 1; i++) {
            if (prices[i] < minPrice) {
                // 确定最小买入点
                minPrice = prices[i];
                if (prices[i + 1] > prices[i]) {
                    // 累加利润值
                    maxProfit += prices[i + 1] - minPrice;
                }
            } else if (prices[i + 1] > prices[i]) {
                // 表示元素一直递增，则取出最大利润值
                maxProfit += prices[i + 1] - prices[i];
            } else if (prices[i + 1] < prices[i]) {
                minPrice = prices[i + 1];// 重置最小买入点
            }
        }
        // 返回最大利润值
        return maxProfit;
    }

    /**
     * 最大优化
     *
     * @param prices
     * @return
     */
    private static int maxProfitV2(int[] prices) {
        // 最大利润值
        int maxProfit = 0;
        for (int i = 0; i < prices.length - 1; i++) {
            if (prices[i + 1] > prices[i]) {
                // 表示元素一直递增，则取出最大利润值
                maxProfit += prices[i + 1] - prices[i];
            }
        }
        // 返回最大利润值
        return maxProfit;
    }
}
