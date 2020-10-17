package com.example.demo;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author Pecker
 * @Description 信号量测试, 会不会有分布式并发问题
 * @since 2020-06-17
 */
public class SemaphoreTest {
    private static Semaphore semphore = new Semaphore(5);

    public static void exec(int num) {
        System.out.println("有效证书个数" + semphore.availablePermits());
        if (semphore.getQueueLength() > 0) {
            System.out.println("当前等待排队的任务数大约为" + semphore.getQueueLength() + "个,请稍候再试...");
        }
        try {
            //获取信号量
            semphore.acquire();
            // 处理业务逻辑
            TimeUnit.SECONDS.sleep(1);
            System.out.println("获取到有效证书,执行业务" + num);
            //System.out.println("--" + System.currentTimeMillis() / 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("============ error");
        } finally {
            //释放信号量
            semphore.release();
        }
    }

    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {
            int finalI = i;
            new Thread(() -> {
                System.out.println("i = " + finalI);
                exec(finalI);
            }).start();

        }
    }
}
