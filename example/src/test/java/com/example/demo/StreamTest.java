package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.example.demo.model.Student;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.System.out;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2020-10-16
 * <p>
 * Stream 操作类型:
 * <p>
 * Intermediate 中间操作：
 * map (mapToInt, flatMap 等)、 filter、 distinct、 sorted、 peek、 limit、 skip、limit、parallel、 sequential、 unordered
 * <p>
 * Terminal 终止操作：
 * forEach、 forEachOrdered、 toArray、 reduce、 collect、 min、 max、 count、 anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 iterator
 * <p>
 * Short-circuiting 短路操作：
 * anyMatch、 allMatch、 noneMatch、 findFirst、 findAny
 */
public class StreamTest {

    public static void main(String[] args) {
        unordered();
    }

    /**
     * 初始化 List : Stream.of() --> collect()  --> Collectors.toList()
     *
     * @return list
     */
    static List<Student> initList() {
        List<Student> studentList = Stream.of(
                new Student("路飞", 22),
                new Student("路飞", 20),
                new Student("红发", 40),
                new Student("白胡子", 50)).collect(Collectors.toList());
        out.println("初始化列表 : " + studentList);
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
        out.println(list);
        return list;
    }

    /**
     * limit and skip
     */
    static void limitAndSkip() {
        // limit 返回 Stream 的前面 n 个元素
        List<Integer> limitList = initList().stream()
                .map(Student::getAge)
                .limit(2).collect(Collectors.toList());
        limitList.forEach(out::println);

        //skip 扔掉前 n 个元素
        List<Integer> skipList = initList().stream().map(Student::getAge)
                .skip(1).collect(Collectors.toList());
        skipList.forEach(out::println);
    }

    /**
     * distinct 过滤出不重复的
     */
    static void distinct() {
        List<String> names = initList().stream().map(Student::getName)
                .distinct()
                .collect(Collectors.toList());
        names.forEach(out::println);
    }

    /**
     * sorted 排序
     */
    static void sorted() {
        //正序
        List<Integer> sortedASC = initList().stream()
                .map(Student::getAge)
                .sorted().collect(Collectors.toList());
        sortedASC.forEach(out::println);

        //倒序
        List<Integer> sortedDESC = initList().stream()
                .map(Student::getAge)
                .sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        sortedDESC.forEach(out::println);

        //按对象某个字段排序,倒序
        List<Student> sortedObject = initList().stream()
                .sorted(Comparator.comparing(Student::getAge, Integer::compareTo).reversed())
                .collect(Collectors.toList());
        sortedObject.forEach(out::println);
    }

    /**
     * 利用 Option 接收 list 最值
     */
    static void maxAndMin() {
        List<Student> students = initList();
        //求最大值
        Optional<Student> max = students.stream()
                .max(Comparator.comparing(Student::getAge));
        max.ifPresent(out::println);

        //求最小值
        Optional<Student> min = students.stream()
                .min(Comparator.comparing(Student::getAge));
        min.ifPresent(out::println);
    }

    /**
     * peek 对每个元素执行操作,可以自定义操作方法
     */
    static void peek() {
        initList().stream()
                .peek(student -> student.setAge(10))
                .collect(Collectors.toList())
                .forEach(out::println);
    }

    /**
     * 可能会并行处理 stream,默认使用的是 ForkJoinPool
     */
    static void parallel() {
        initList().stream()
                .parallel()
                //多执行几遍可以看到这里打印的顺序和 list 的数据是在变化的,就是因为并行流的原因
                .peek(o -> {
                    out.println(Thread.currentThread());
                    out.println(o.getAge());
                })
                .collect(Collectors.toList())
                .forEach(out::println);
    }

    /**
     * 顺序处理流,我理解的和不加parallel 的效果是一样的
     */
    static void sequential() {
        initList().stream()
                .sequential()
                .peek(o -> {
                    out.println(Thread.currentThread());
                    out.println(o.getAge());
                })
                .collect(Collectors.toList())
                .forEach(out::println);
    }

    /**
     * unordered 跟集合本身是否是有序无关,跟是否并行化执行有关
     */
    static void unordered() {
        Set<Integer> l = new TreeSet<>();
        l.add(1);
        l.add(10);
        l.add(3);
        l.add(-3);
        l.add(-4);

        out.println("Serial Stream");
        l.stream().map(s -> s + " ").forEach(out::print);
        out.println();
        l.stream().map(s -> s + " ").unordered().forEach(out::print);
        out.println("\n");

        System.out.println("Unordered Operations on a Parallel Stream");
        l.stream().parallel().map(s -> s + " ").forEach(System.out::print);
        System.out.println();
        l.stream().unordered().map(s -> s + " ").parallel().forEach(System.out::print);
        System.out.println("\n");

        System.out.println("Ordered Operations on a Parallel Stream");
        l.stream().parallel().skip(2).limit(2).findFirst().ifPresent(System.out::print);
        System.out.println();
        l.stream().unordered().parallel().skip(2).limit(2).findFirst().ifPresent(System.out::print);
        System.out.println("\n");
    }

    /**
     * Stream#reduce()这个方法的主要作用是把 Stream 元素组合起来
     * <p>
     * identity : 初始值
     * accumulator : 组合 Stream 的方法
     */
    static void reduce() {
        // 利用 reduce() 求和 sum
        Integer reduce = initList().stream().map(Student::getAge)
                .reduce(0, Integer::sum);
        out.println(reduce);

        //拼接字符串
        String str = initList().stream().map(Student::getName)
                .reduce("identity-", String::concat);
        out.println(str);
    }

    /**
     * Stream#match()返回一个 boolean 值
     * <p>
     * allMatch：Stream 中全部元素符合传入的 predicate，返回 true
     * anyMatch：Stream 中只要有一个元素符合传入的 predicate，返回 true
     * noneMatch：Stream 中没有一个元素符合传入的 predicate，返回 true
     */
    static void match() {
        boolean allMatch = initList().stream()
                .allMatch(student -> student.getAge() > 30);
        out.println(allMatch);//false

        boolean anyMatch = initList().stream()
                .anyMatch(student -> student.getAge() > 30);
        out.println(anyMatch);//true

        boolean noneMatch = initList().stream()
                .noneMatch(student -> student.getAge() > 100);
        out.println(noneMatch);//true
    }

    /**
     * list 转 Map
     *
     * @return return
     */
    static Map<String, Integer> listToMap() {
        Map<String, Integer> map = initList().stream()
                .collect(Collectors.toMap(Student::getName, Student::getAge));
        out.println(map);
        return map;
    }


    /**
     * list 映射出某个字段
     */
    static void mapping() {
        List<String> list = initList().stream()
                .map(Student::getName)
                .collect(Collectors.toList());
        out.println(list);
    }

    /**
     * mapToInt 返回值是 IntStream
     */
    static void mapToInt() {
        List<Integer> intList = initList().stream()
                .mapToInt(Student::getAge)
                .boxed().collect(Collectors.toList());
    }

    /**
     * flatMap 把 Stream 中的层级结构扁平化，就是将最底层元素抽出来放到一起
     */
    static void flapMap() {
        List<String> strs = Arrays.asList("风,萧,萧,兮,易,水,寒", "壮,士,一,去,兮,不,复,还");
        List<String[]> strArray = strs.stream().map(str -> str.split(","))
                .collect(Collectors.toList());
        out.println("strArray size = " + strArray.size());
        out.println("strArray => " + JSON.toJSONString(strArray));

        List<String> strList = strs.stream().map(str -> str.split(","))
                .flatMap(Arrays::stream).collect(Collectors.toList());
        out.println("strList size = " + strList.size());
        out.println("strList => " + JSON.toJSONString(strList));
    }

    /**
     * 字符串拼接
     */
    static void joining() {
        String names = initList().stream()
                .map(Student::getName).collect(Collectors.joining(",", "[", "]"));
        out.println(names);
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

        out.println(listMap);
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
        out.println(listMap);
        return listMap;
    }

}
