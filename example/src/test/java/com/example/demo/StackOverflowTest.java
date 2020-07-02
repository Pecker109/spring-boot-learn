package com.example.demo;

/**
 * @author Pecker
 * @Description 栈溢出示例
 * @since 2020-06-28
 *
 * 当启动一个新线程时，JVM就会给这个线程分配一个Java栈（这个栈的内存大小由-Xss参数来设置）。
 *
 * 一个Java栈的基本单位是帧，每一次函数调用就会生成栈帧，占用一定的栈空间。当函数本身需要的内存过大，或者函数调用函数（依赖调用或者递归调用）太深，超过了-Xss设置的内存大小，就会抛出StackOverflowError异常。
 *
 * -Xss：默认值 1M，控制每个线程占用的内存，这个参数决定了函数调用的最大深度。如果设置的太小可能会很容易出现 StackOverflowError 异常。
 * JDK 5.0以后每个线程堆栈大小为1M，以前每个线程堆栈大小为256K。在相同物理内存下，减小这个值能生成更多的线程。但是操作系统对一个进程内的线程数还是有限制的，不能无限生成，经验值在3000~5000左右。
 */
public class StackOverflowTest {

    private static int deep = 1;

    /**
     * 通过无限递归来模拟栈溢出
     */
    private static void recursion() {
        deep++;
        recursion();
    }

    public static void main(String[] args) {
        try {
            recursion();
        } catch (Throwable e) { // catch 捕获的是 Throwable，而不是 Exception。因为 StackOverflowError 不属于 Exception 的子类。
            System.out.println("Stack deep : " + deep);
            e.printStackTrace();
        }

        // 不让进程结束，便于使用分析工具来查看内存情况
        try {
            Thread.sleep(24 * 60 * 60 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * 当启动一个新线程时，JVM就会给这个线程分配一个Java栈（这个栈的内存大小由-Xss参数来设置）。
     *
     * 一个Java栈的基本单位是帧，每一次函数调用就会生成栈帧，占用一定的栈空间。当函数本身需要的内存过大，或者函数调用函数（依赖调用或者递归调用）太深，超过了-Xss设置的内存大小，就会抛出StackOverflowError异常。
     *
     * -Xss：默认值 1M，控制每个线程占用的内存，这个参数决定了函数调用的最大深度。如果设置的太小可能会很容易出现 StackOverflowError 异常。
     * JDK 5.0以后每个线程堆栈大小为1M，以前每个线程堆栈大小为256K。在相同物理内存下，减小这个值能生成更多的线程。但是操作系统对一个进程内的线程数还是有限制的，不能无限生成，经验值在3000~5000左右。
     */
}
