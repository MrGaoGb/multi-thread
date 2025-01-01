package com.mrgao.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 堆内存溢出之OOM
 * @Author Mr.Gao
 * @Date 2024/12/31 0:12
 *
 * <p> jvm 参数
 *     -Xms200m -Xmx200m -XX:+HeapDumpOnOutOfMemoryError
 *
 *     -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/path/to/dump.hprof
 */
public class HeapOom {

    byte[] bytes = new byte[1024 * 1024];

    public static void main(String[] args) throws Exception {

        List<HeapOom> list = new ArrayList<HeapOom>();

        while (true) {
            HeapOom h = new HeapOom();
            list.add(h);
            Thread.sleep(10);
        }

    }
}
