package com.example.demo;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Pecker
 * @since 2019-11-06
 */
@Component
public class TaskTest {

    @Async
    public void doTask(int i) {
        System.out.println("开始执行======i:" + i);
    }

    //异步方法内部多线程执行
    @Async
    public void doLoopTask(int i) {
        ExecutorService pool = Executors.newCachedThreadPool();
        try {
            System.out.println("开始执行======i:" + i);
            for (int j = 0; j < 50; j++) {
                int finalJ = j;
                Runnable runnable = () -> {
                    try {
                        System.out.println("i = " + i + "===== j=" + finalJ);
                        Thread.sleep(100);
                        System.out.println("执行完毕 i = " + i + "===== j=" + finalJ);
                    } catch (Exception e) {
                        //  handle exception
                    }
                };
                //执行任务
                pool.execute(runnable);
            }
        } finally {
            pool.shutdown();
        }
    }

    public static Random random = new Random();

    public void doTaskOne() throws Exception {
        System.out.println("开始做任务一");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        System.out.println("完成任务一，耗时：" + (end - start) + "毫秒");
    }

    public void doTaskTwo() throws Exception {
        System.out.println("开始做任务二");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        System.out.println("完成任务二，耗时：" + (end - start) + "毫秒");
    }

    public void doTaskThree() throws Exception {
        System.out.println("开始做任务三");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        System.out.println("完成任务三，耗时：" + (end - start) + "毫秒");
    }
}
