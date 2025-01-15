package com.mrgao.java.base.collection;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mr.Gao
 * @apiNote:HashMap中常用方法
 * @date 2025/1/15 17:05
 */
public class TestHashMap {

    public static void main(String[] args) {

        // getOrDefault()方法
        //testGetOrDefault();

        // forEach()方法
        //testForeach();

        // merge()方法
        //testMerge();

        // putIfAbsent()方法
        //testPutIfAbsent();

        // computer()方法
        //testComputer();

        // replace()方法
        testReplace();
    }

    /**
     * getOrDefault()方法
     */
    private static void testGetOrDefault() {
        Map<String, String> map = new HashMap<>();
        map.put("newKey", "100000");

        String key = "newKey2";
        String defaultValue = "50000";

        // 老写法
        String oldValue = defaultValue;
        if (map.containsKey(key)) {
            oldValue = map.get(key);
        }
        System.out.println("老写法:" + oldValue);

        System.out.println("----------------------------");

        // 新写法
        String newValue = map.getOrDefault(key, defaultValue);

        System.out.println("新写法:" + newValue);
    }


    /**
     * forEach()方法
     */
    private static void testForeach() {
        Map<String, String> map = new HashMap<>();
        map.put("newKey", "100000");
        map.put("newKey2", "50000");

        // 老写法
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println("老写法=>>" + entry.getKey() + ":" + entry.getValue());
        }

        System.out.println("----------------------------");

        // 新写法
        map.forEach((key, value) -> {
            System.out.println("新写法=>>" + key + ":" + value);
        });
    }


    /**
     * merge()方法
     * <p>
     * merge()方法，如果key不存在，则put对应的值，如果key存在，则可以对旧值进行修改或覆盖(oldVal,newVal)。
     * </p>
     */
    private static void testMerge() {
        Map<String, Integer> oldMap = new HashMap<>();
        Map<String, Integer> newMap = new HashMap<>();

        List<String> list = Arrays.asList("apple", "orange", "banana", "orange");

        // 老写法
        for (String fruit : list) {
            if (oldMap.containsKey(fruit)) {
                oldMap.put(fruit, oldMap.get(fruit) + 1);
            } else {
                oldMap.put(fruit, 1);
            }
        }
        System.out.println("老写法==> " + oldMap);

        System.out.println("----------------------------");

        for (String fruit : list) {
            newMap.merge(fruit, 1, Integer::sum);
        }

        System.out.println("新写法==> " + newMap);
    }


    /**
     * putIfAbsent()方法
     * <p>
     * putIfAbsent()方法，如果key不存在，则put对应的Key返回值为null，
     * 如果key存在，则不put，返回key对应的oldValue(返回旧值)。
     * </p>
     */
    private static void testPutIfAbsent() {
        Map<String, Integer> oldMap = new HashMap<>();
        Integer put = oldMap.put("timi", 5);
        System.out.println("timi==> " + put);
        Integer put1 = oldMap.put("jim", 6);
        System.out.println("jim==> " + put1);


        Map<String, Integer> newMap = new HashMap<>();
        newMap.put("timi", 5);
        newMap.put("jim", 6);

        // 老写法
        if (!oldMap.containsKey("jim")) {
            oldMap.put("jim", 7);
        }

        System.out.println("老写法==> " + oldMap);

        System.out.println("----------------------------");

        // 新写法
        Integer jim = newMap.putIfAbsent("jim", 7);
        System.out.println("jim==> " + jim);

        System.out.println("新写法==> " + newMap);
    }

    /**
     * computer()方法
     * <p>
     * 作用等同于merge()方法
     * </p>
     */
    private static void testComputer() {

        Map<String, Integer> oldMap = new HashMap<>();
        Map<String, Integer> newMap = new HashMap<>();

        List<String> list = Arrays.asList("apple", "orange", "banana", "orange");


        // 老写法
        for (String fruit : list) {
            if (oldMap.containsKey(fruit)) {
                oldMap.put(fruit, oldMap.get(fruit) + 1);
            } else {
                oldMap.put(fruit, 1);
            }
        }
        System.out.println("老写法==> " + oldMap);

        System.out.println("----------------------------");

        // 新写法
        for (String fruit : list) {
            newMap.compute(fruit, (key, val) -> {
                if (val == null) {
                    return 1;
                }
                return val + 1;
            });
        }

        System.out.println("新写法==> " + newMap);


    }

    /**
     * replace()方法
     * <p>
     * 如果key存在，则替换key对应的value，返回旧值，如果key不存在，则不替换，返回null。
     * </p>
     */
    private static void testReplace() {
        Map<String, Integer> map = new HashMap<>();
        map.put("apple", 1);
        map.put("orange", 2);

        // 替换旧值(存在的KEY)
        Integer apple = map.replace("apple", 3);
        System.out.println("apple==>" + apple);
        System.out.println("map==>" + map);

        // 替换新值(不存在的KEY)
        Integer orange = map.replace("banana", 4);
        System.out.println("orange==>" + orange);
        System.out.println("map==>" + map);
    }
}
