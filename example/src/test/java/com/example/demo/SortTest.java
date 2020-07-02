package com.example.demo;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2020-06-04
 */
public class SortTest {

    public static void main(String[] args) {
        List<Obj> list = Arrays.asList(
                new Obj("政府", null),
                new Obj("政府", new BigDecimal("1216.23")),
                new Obj("商业", new BigDecimal("123.23")),
                new Obj("PPD", new BigDecimal("123.23")),
                new Obj("合作", new BigDecimal("127.23")),
                new Obj("合作", new BigDecimal("125.23")),
                new Obj("咨询", null),
                new Obj(null, null)
        );
        List<String> sortList = Arrays.asList("PPD", "政府", "合作");

        Map<String, String> map = new HashMap() {{
            put("合作", "aa");
            put("政府", "bbb");
        }};

        System.out.println(JSON.toJSONString(list));
        System.out.println(JSON.toJSONString(map.keySet()));

        //按照list 中的对象的某个属性排序
        list.stream().sorted(Comparator.comparing(Obj::getPrice,Comparator.nullsFirst(BigDecimal::compareTo)).reversed()).collect(Collectors.toList()).forEach(System.out::println);

        //按照自定义排序
        list.stream().sorted(
                Comparator.comparing(Obj::getName, (x, y) -> {
                    if (x == null && y != null) {
                        return 1;
                    } else if (x != null && y == null) {
                        return -1;
                    } else if (x == null && y == null) {
                        return -1;
                    } else {
                        //按照读取的list顺序排序
                        for (String sort : sortList) {
                            if (sort.equals(x) || sort.equals(y)) {
                                if (x.equals(y)) {
                                    return 0;
                                } else if (sort.equals(x)) {
                                    return -1;
                                } else {
                                    return 1;
                                }
                            }
                        }
                        return 0;
                    }
                }).thenComparing(Comparator.comparing(Obj::getPrice, Comparator.nullsFirst(BigDecimal::compareTo)).reversed())
        ).collect(Collectors.toList()).forEach(System.out::println);
        ;


    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Obj {
        private String name;
        private BigDecimal price;
    }
}
