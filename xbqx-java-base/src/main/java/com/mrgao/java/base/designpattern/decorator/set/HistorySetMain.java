package com.mrgao.java.base.designpattern.decorator.set;

/**
 * @Description 验证HistorySet集合
 * @Author Mr.Gao
 * @Date 2025/4/16 0:07
 */
public class HistorySetMain {

    public static void main(String[] args) {
        HistorySet<String> historySet = new HistorySet<>();
        historySet.add("A");
        historySet.add("B");
        historySet.add("C");
        historySet.add("D");
        historySet.add("E");

        // 移除元素D
        historySet.remove("D");
        historySet.remove("A");
        // 移除一个不存在的元素
        historySet.remove("F");

        System.out.println("移除的历史元素集合:" + historySet);
    }
}
