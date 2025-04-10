package com.mrgao.java.base.collection;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Mr.Gao
 * @apiNote:
 * @date 2025/4/10 19:31
 */
public class TestLinkedHashMap {

    public static void main(String[] args) {

        // 按照插入顺序
        Map<String, String> map = new LinkedHashMap<>();
        map.put("A", "1");
        map.put("B", "2");
        map.put("C", "3");
        map.put("D", "4");
        System.out.println(map.keySet());

        // 按照访问顺序
        Map<String, String> mapOrder = new LinkedHashMap<>(4, 0.75f, true);
        mapOrder.put("A", "1");
        mapOrder.put("B", "2");
        mapOrder.put("C", "3");
        mapOrder.put("D", "4");

        System.out.println("get(Before):" + mapOrder.keySet());

        // get|put 操作会触发元素移动到末尾
        //String a = mapOrder.get("A");
        //System.out.println("get(元素) a:" + a);

        mapOrder.put("B", "6");

        System.out.println("get(After):" + mapOrder.keySet());


    }

}
