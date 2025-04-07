package com.mrgao.java.base.streamapi;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Mr.Gao
 * @apiNote:
 * @date 2025/4/7 10:01
 */
public class Stream_API {
    public static void main(String[] args) {
        // 流的三大部分：创建流、N个中间操作、一个终止操作
        // 创建流
        Stream<String> stream = Stream.of("hello", "world", "java");
        // 流的concat
        //Stream<String> stream2 = Stream.concat(stream, Stream.of("python"));
        // 得到stream2中的所有元素
        //List<String> list = stream2.collect(Collectors.toList());
        //System.out.println(list);
        // 创建流Stream.builder()
        //Stream<Object> build = Stream.builder().add("hello").add("world").add("java").build();


        // 创建一个线程安全的list(底层执行的所有方法都会被synchronized加锁处理)
        //List<Object> objects = Collections.synchronizedList(new ArrayList<>());

        System.out.println("==========Flap 操作=========");
        stream.filter(s -> s.contains("l")) // 筛选出包含l的字符串
                .peek(s -> System.out.println("###flap之前:" + s))
                .flatMap(s -> {
                    // @1
                    return s.chars().mapToObj(n -> (char) n);
                    //return s.chars().boxed();

                    // @2
                    //char[] charArray = s.toCharArray();
                    //List<String> list = new ArrayList<>();
                    //for (char c : charArray) {
                    //    System.out.println(c);
                    //    list.add(String.valueOf(c));
                    //}
                    //return list.stream();

                    // @3
                    //String[] ls = s.split("o");
                    //return Stream.of(ls);
                })
                .peek(s -> System.out.println("###flap之后:" + s))
                .forEach(s -> System.out.println("@forEach:" + s));

        System.out.println("===========peek 操作(Begin)======");
        // peek()：用于在流的中间操作中，对流中的元素进行一些处理，但不会改变流中的元素。
        Stream.of("one", "two", "three", "four")
                .filter(e -> e.length() > 3)
                .peek(e -> System.out.println("peek value(前): " + e))
                .map(String::toUpperCase)
                .peek(e -> System.out.println("peek value(后): " + e))
                .collect(Collectors.toList());

        System.out.println("===========peek 操作(End)======");

        /**
         * TODO filter 和 takeWhile 的区别
         * filter: 无条件遍历流中的每一个元素
         * takeWhile: 遇到第一个不满足条件的元素，就停止遍历
         */
        System.out.println("====filter 和 takeWhile 的区别====");
        Stream.of("one", "two", "three", "four")
                .peek(s -> System.out.println("Filtered value:(前)" + s))
                .filter(s -> s.length() > 3)
                .peek(s -> System.out.println("Filtered value:(后) " + s))
                .forEach(s -> System.out.println("filter的ForEach：" + s));

        // JDK9 新增的 takeWhile() 方法 ：用于在流的中间操作中，对流中的元素进行一些处理，但不会改变流中的元素。
        Stream.of("one", "two", "three", "four")
                .peek(s -> System.out.println("takeWhile value:(前)" + s))
                //.takeWhile(s -> s.length() > 3) // 遇到第一个不满足条件的元素，就停止遍历
                .takeWhile(s -> s.length() <= 3) // 遇到第一个不满足条件的元素，就停止遍历
                .peek(s -> System.out.println("takeWhile value:(后) " + s))
                .forEach(s -> System.out.println("takeWhile的ForEach：" + s));
    }
}
