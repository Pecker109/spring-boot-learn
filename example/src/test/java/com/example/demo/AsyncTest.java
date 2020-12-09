package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Pecker
 * @since 2019-11-06
 */
@RunWith(SpringRunner.class)
@SpringBootTest
//@EnableAsync
public class AsyncTest {

    @Autowired
    TaskTest taskTest;

    /**
     * 测试@Async 异步调用
     */
    @Test
    public void testAsync() throws InterruptedException {
        System.out.println("####开始执行..  start");
        //这种写法是for循环里面调用@Async异步方法,所以for循环里面是异步执行
        for (int i = 0; i < 50; i++) {
            try {
                //Thread.sleep(1000);
                taskTest.doTask(i);
            } catch (Exception e) {
                // handle exception
            }
        }
        System.out.println("####结束执行..  end");
        Thread.sleep(500 * 1000L);
    }

    /**
     * 测试线程池调用
     */
    public void testThreadPool() throws InterruptedException {

        System.out.println("####开始执行..  start");
        //这种写法是for循环启用多线程执行,即使不加@Async也是多个线程并发执行,可以把@Async去掉测试
        ExecutorService pool = Executors.newCachedThreadPool();
        for (int i = 0; i < 50; i++) {
            int finalI = i;
            Runnable run = () -> {
                try {
                    Thread.sleep(1000);
                    taskTest.doTask(finalI);
                } catch (Exception e) {
                    //  handle exception
                }
            };
            pool.execute(run);

        }
        pool.shutdown();

        System.out.println("####结束执行..  end");
        Thread.sleep(500 * 1000L);
    }

    /**
     * 异步调用,被调用方法也是用多线程执行
     * @throws InterruptedException
     */
    @Test
    public void testLoopAsync() throws InterruptedException {
        System.out.println("####开始执行..  start");
        //这种写法是for循环里面调用@Async异步方法,所以for循环里面是异步执行
        for (int i = 0; i < 10; i++) {
            try {
                //Thread.sleep(1000);
                taskTest.doLoopTask(i);
            } catch (Exception e) {
                // handle exception
            }
        }
        System.out.println("####结束执行..  end");
        Thread.sleep(500 * 1000L);
    }
}
