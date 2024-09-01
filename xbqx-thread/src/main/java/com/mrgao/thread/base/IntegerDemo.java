package com.mrgao.thread.base;

/**
 * @Description TODO
 * @Author Mr.Gao
 * @Date 2024/9/1 19:03
 */
public class IntegerDemo {

    public static void main(String[] args) {

        // Integer 缓存范围：-128~127 该范围内表示同一个引用对象
        Integer num1 = 1;
        Integer num2 = 1;
        // == 对于基本类型来说，比较的是值 对于引用类型来说比较的是对象的引用地址
        // equals 对于引用类型来说 比较的是对象的引用地址
        System.out.println(num1 == num2);
        System.out.println(num1.equals(num2));
        Integer num3 = 200;
        Integer num4 = 200;
        System.out.println(num3 == num4);
        System.out.println(num3.equals(num4));
        Integer num5 = num3;
        System.out.println(num5 == num3);

        System.out.println("=======临界值=======");
        // 测试下临界值 最小值
        Integer num6 = -128;
        Integer num7 = -128;
        System.out.println(num6 == num7);
        // 最大值
        Integer num8 = 127;
        Integer num9 = 127;
        System.out.println(num8 == num9);

        // 测试下超过范围
        System.out.println("=======测试下超过范围=======");
        Integer num10 = -129;
        Integer num11 = -129;
        System.out.println(num10 == num11);
        Integer num12 = 128;
        Integer num13 = 128;
        System.out.println(num12 == num13);

    }
}
