package com.example.demo;


import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author Pecker
 * @Description JDK8函数式接口Function、Consumer、Predicate、Supplier
 * @since 2021-06-23
 */
public class FunctionTest {

    /**
     * Function<T, R> : 接收一个入参 T,返回结果 R
     * Consumer<T> : 接收一个入参 T,没有返回结果
     * Predicate<T> : 接收一个入参 T, 返回 Boolean 值
     * Supplier<T> : 没有入参,返回结果 T
     */

    public static void main(String[] args) {
        //functionApplyTest();
        //functionComposeTest();
        //consumerAccept();
        //predicateTest1();
        //predicateTest2();
        supplierTest();
    }

    // 测试 Function#apply() 方法
    public static void functionApplyTest() {
        int value = applyValue(v -> v + 10, 1);
        System.out.println(value);
    }

    /**
     * @param function 要执行的方法,T : 入参类型  R : 返回结果类型
     * @param input    入参
     * @return 返回以 input 为入参,执行 function 的结果
     */
    static int applyValue(Function<Integer, Integer> function, Integer input) {
        return function.apply(input);
    }

    public static void functionComposeTest() {
        // 执行顺序为:(1 + 10) * 2
        int value = composeValue(v1 -> v1 + 10, v2 -> v2 * 2, 1);
        System.out.println(value);
    }

    /**
     * @param function1 before_function
     * @param function2 after_function
     * @param input     before_function 的入参
     * @return 先以 input 为入参执行 function1,得到的结果为 function2 的入参执行 function2,返回结果
     */
    static int composeValue(Function<Integer, Integer> function1, Function<Integer, Integer> function2, Integer input) {
        return function2.compose(function1).apply(input);
    }

    public static void consumerAccept() {
        // 没有返回结果
        consumerValue(v -> System.out.println(v * 2), 3);
    }

    /**
     * 以 input 为入参,执行 consumer 方法,没有返回值
     *
     * @param consumer consumer
     * @param input    入参
     */
    static void consumerValue(Consumer<Integer> consumer, Integer input) {
        consumer.accept(input);
    }

    public static void predicateTest1() {
        // (x) -> x == 3 输入一个参数x，进行比较操作，返回一个布尔值
        // 所以该lambda表达式可以实现Predicate接口
        System.out.println(predicateTest1(3, (x) -> x == 3));
    }

    /**
     * 输入一个 value,执行 predicate 方法,比较结果
     *
     * @param value     入参
     * @param predicate 断言表达式
     * @return 返回一个 Boolean 值
     */
    public static boolean predicateTest1(int value, Predicate<Integer> predicate) {
        return predicate.test(value);
    }

    /**
     * 巧用 predicate
     */
    public static void predicateTest2() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        // Predicate<Integer> predicate = n -> true
        // n 是一个参数传递到 Predicate 接口的 test 方法
        // n 如果存在则 test 方法返回 true

        System.out.println("输出所有数据:");

        // 传递参数 n
        eval(list, n -> true);

        // Predicate<Integer> predicate1 = n -> n%2 == 0
        // n 是一个参数传递到 Predicate 接口的 test 方法
        // 如果 n%2 为 0 test 方法返回 true
        System.out.println("\n输出所有偶数:");
        eval(list, n -> n % 2 == 0);

        // Predicate<Integer> predicate2 = n -> n > 3
        // n 是一个参数传递到 Predicate 接口的 test 方法
        // 如果 n 大于 3 test 方法返回 true
        System.out.println("\n输出大于 3 的所有数字:");
        eval(list, n -> n > 3);
    }

    public static void eval(List<Integer> list, Predicate<Integer> predicate) {
        for (Integer n : list) {
            if (predicate.test(n)) {
                System.out.print(n + " ");
            }
        }

//      list.forEach(n -> {
//          if (predicate.test(n)) {
//              System.out.print(n + " ");
//          }
//      });
    }


    public static void supplierTest() {
        String name = "冷冷";
        // () -> name.length() 无参数，返回一个结果（字符串长度）
        System.out.println(supplierTest(name::length));
    }

    public static Integer supplierTest(Supplier<Integer> supplier) {
        return supplier.get();
    }

}
