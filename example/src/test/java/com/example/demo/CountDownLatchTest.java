package com.example.demo;

import java.util.concurrent.CountDownLatch;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2020-08-16
 */
public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(10);

        for (int i = 0; i < 10; i++) {
            System.out.println("foreach" + i);
            TaskExecutor task = new TaskExecutor(latch);
            task.start();
        }
        System.out.println("latch wait");
        latch.await();
        System.out.println("latch over");
    }

    static class TaskExecutor extends Thread {

        CountDownLatch latch;

        public TaskExecutor(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                System.out.println("executor task");
                Thread.sleep(5000);
                System.out.println("executor task over");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }
    }
}
