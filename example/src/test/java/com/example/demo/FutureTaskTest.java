package com.example.demo;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author Pecker
 * @Description FutureTask 用例 ,FutureTask#get() 会被阻塞
 * @since 2021-03-24
 */
public class FutureTaskTest {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        long startTime = System.currentTimeMillis();

        //input2生成，
        FutureTask<Integer> input2_futureTask = new FutureTask<>(new Callable<Integer>() {

            @Override
            public Integer call() throws Exception {
                System.out.println("计算 i2 ...");
                Thread.sleep(3000);
                System.out.println("i2 计算完...");
                return 5;
            }
        });

        new Thread(input2_futureTask).start();

        //input1生成，需要耗费2秒
        FutureTask<Integer> input1_futureTask = new FutureTask<>(new Callable<Integer>() {

            @Override
            public Integer call() throws Exception {
                System.out.println("计算 i1 ...");
                Thread.sleep(2000);
                System.out.println("i1 计算完...");
                return 3;
            }
        });
        new Thread(input1_futureTask).start();


        Integer i2 = input2_futureTask.get();
        Integer i1 = input1_futureTask.get();
        System.out.println("i1 和 i2 计算完毕,执行算法...");
        System.out.println(algorithm(i1, i2));
        long endTime = System.currentTimeMillis();
        System.out.println("用时：" + (endTime - startTime));
    }

    //这是我们要执行的算法
    public static int algorithm(int input, int input2) {
        return input + input2;
    }
}
