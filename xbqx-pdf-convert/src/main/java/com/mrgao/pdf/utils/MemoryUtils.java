package com.mrgao.pdf.utils;

import java.util.Arrays;

/**
 * @Description 打印内存信息
 * @Author Mr.Gao
 * @Date 2025/1/1 16:43
 */
public class MemoryUtils {

    public static void prinfMemoryInfo(Object... param) {
        System.out.println("-----------打印内存信息(Start)----------：" + Arrays.toString(param));
        Runtime runtime = Runtime.getRuntime();
        System.out.println("总内存：" + runtime.totalMemory() / 1024 / 1024 + "M");
        System.out.println("已用内存：" + (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024 + "M");
        System.out.println("剩余内存：" + runtime.freeMemory() / 1024 / 1024 + "M");
        System.out.println("最大内存：" + runtime.maxMemory() / 1024 / 1024 + "M");
        System.out.println(" processors: " + runtime.availableProcessors());
        System.out.println("------------打印内存信息(End)-------------------");

    }
}
