package com.mrgao.likou.array.base;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mr.Gao
 * @apiNote:对变量的引用进行结果值重写(String不可变,Map是可变的)
 * @date 2024/12/25 17:55
 */
public class OverwriteVariableTest {


    public static String overwriteVal(String val) {
        System.out.println("当前值:" + val);
        val = "无所谓";
        System.out.println("修改后值:" + val);
        return val;
    }

    public static Map<String, String> overwriteVal(Map<String, String> map) {
        System.out.println("当前值:" + map);
        map.put("overwrite", "2");
        System.out.println("修改后值:" + map);
        return map;
    }


    /**
     * <p>
     * 1、String 是不可变的，所以 overwriteVal 方法中修改了 String 的值，但是对原来的 String 没有影响。
     * 2、Map 是可变的，所以 overwriteVal 方法中修改了 Map 的值，对原来的 Map 也有影响。
     * <p>
     * ==== 运行结果
     * 1:hello
     * 当前值:hello
     * 修改后值:无所谓
     * 无所谓
     * 2:hello
     * ====================
     * 3:{before=1}
     * 当前值:{before=1}
     * 修改后值:{before=1, overwrite=2}
     * {before=1, overwrite=2}
     * 4:{before=1, overwrite=2}
     * </p>
     *
     * @param args
     */
    public static void main(String[] args) {
        String content = "hello";
        System.out.println("1:" + content);
        System.out.println(overwriteVal(content));
        System.out.println("2:" + content);

        System.out.println("====================");

        Map<String, String> map = new HashMap<>();
        map.put("before", "1");
        System.out.println("3:" + map);
        System.out.println(overwriteVal(map));
        System.out.println("4:" + map);

    }

}
