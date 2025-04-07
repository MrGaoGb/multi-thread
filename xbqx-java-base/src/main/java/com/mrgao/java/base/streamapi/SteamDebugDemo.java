package com.mrgao.java.base.streamapi;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Mr.Gao
 * @apiNote:Stream流调试
 * @date 2025/3/31 9:54
 */
public class SteamDebugDemo {
    public static void main(String[] args) {
        //  创建一个流，对流中的元素进行去重、映射、过滤等操作，并返回一个结果列表
        List<Num> numList = Stream.of(1, 2, 3, 2, 4, 3, 6, 7, 4, 9, 10)
                .distinct()
                .map(num -> new Num("name:" + num, num))
                .filter(num -> !num.num.equals(1))
                .filter(num -> !num.num.equals(6))
                .collect(Collectors.toList());
        // 输出结果
        System.out.println(JSONObject.toJSONString(numList));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Num {

        private String name;

        private Integer num;

    }
}
