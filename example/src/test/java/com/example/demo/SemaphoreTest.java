package com.example.demo;

import java.util.concurrent.Semaphore;

/**
 * @author Pecker
 * @Description 信号量测试
 * @since 2020-06-17
 */
public class SemaphoreTest {
    private static Semaphore semphore = new Semaphore(5);

    public static void exec(int num) {
        System.out.println("有效证书个数" + semphore.availablePermits());
        if (semphore.getQueueLength() > 0) {
            System.out.println("当前等待排队的任务数大于100，请稍候再试...");
        }
        try {
            semphore.acquire();
            // 处理核心逻辑
            //TimeUnit.SECONDS.sleep(1);
            System.out.println(num);
            //System.out.println("--" + System.currentTimeMillis() / 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("============ error");
        } finally {
            semphore.release();
        }
    }

    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {
            int finalI = i;
            new Thread(()->{
                System.out.println("i = " + finalI);
                exec(finalI);
            }).start();

        }
    }
}
