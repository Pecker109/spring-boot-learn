package com.example.demo;

import com.alibaba.fastjson.JSON;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2020-10-16
 */
public class CollectionTest {

    public static void main(String[] args) {
        joining();
    }

    /**
     * 初始化 List : Stream.of() --> collect()  --> Collectors.toList()
     *
     * @return list
     */
    static List<Student> initList() {
        List<Student> studentList = Stream.of(
                new Student("路飞", 22),
                new Student("红发", 40),
                new Student("白胡子", 50)).collect(Collectors.toList());
        System.out.println(studentList);
        return studentList;
    }

    /**
     * 过滤
     *
     * @return return
     */
    static List<Student> filter() {
        List<Student> list = initList().stream()
                .filter(stu -> stu.getAge() < 40)
                .collect(Collectors.toList());
        System.out.println(list);
        return list;
    }

    /**
     * list 映射出某个字段
     */
    static void mapping() {
        List<String> list = initList().stream()
                .map(Student::getName)
                .collect(Collectors.toList());
        System.out.println(list);
    }

    /**
     * 利用 Option 接收list最值
     */
    static void maxAndMin() {
        List<Student> students = initList();
        Optional<Student> max = students.stream()
                .max(Comparator.comparing(Student::getAge));
        Optional<Student> min = students.stream()
                .min(Comparator.comparing(Student::getAge));
        max.ifPresent(System.out::println);
        min.ifPresent(System.out::println);
    }

    /**
     * 利用 reduce() 求和 sum
     */
    static void reduce() {
        Integer reduce = initList().stream().map(Student::getAge).reduce(0, Integer::sum);
        System.out.println(reduce);
    }

    /**
     * list 转 Map
     *
     * @return return
     */
    static Map<String, Integer> toMap() {
        Map<String, Integer> map = initList().stream().collect(Collectors.toMap(Student::getName, Student::getAge));
        System.out.println(map);
        return map;
    }

    static void flapMap() {
        List<String> strs = Arrays.asList("风,萧,萧,兮,易,水,寒", "壮,士,一,去,兮,不,复,还");
        List<String[]> strArray = strs.stream().map(str -> str.split(",")).collect(Collectors.toList());
        List<String> strList = strs.stream().map(str -> str.split(",")).flatMap(Arrays::stream).collect(Collectors.toList());

        System.out.println("strArray => " + JSON.toJSONString(strArray));
        System.out.println("strList => " + JSON.toJSONString(strList));
    }

    /**
     * 字符串拼接
     */
    static void joining() {
        String names = initList().stream()
                .map(Student::getName).collect(Collectors.joining(",", "[", "]"));
        System.out.println(names);
    }

    /**
     * 将 list 按照对象的某一条件分组成 Map
     *
     * @return 只有两组 true 的一组,false 的一组
     */
    static Map<Boolean, List<Student>> partitioningBy() {
        List<Student> students1 = new ArrayList<>(3);
        students1.add(new Student("路飞", 23));
        students1.add(new Student("红发", 40));
        students1.add(new Student("白胡子", 50));

        Map<Boolean, List<Student>> listMap = students1.stream().collect(
                Collectors.partitioningBy(student -> student.getName().equals("路飞")
                ));

        System.out.println(listMap);
        return listMap;
    }

    /**
     * 分组
     *
     * @return key:分组值
     */
    static Map<String, List<Student>> groupingBy() {
        Map<String, List<Student>> listMap =
                initList().stream().collect(
                        Collectors.groupingBy(Student::getName));
        System.out.println(listMap);
        return listMap;
    }


}
