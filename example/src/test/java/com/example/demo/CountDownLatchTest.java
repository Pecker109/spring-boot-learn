package com.example.demo;

import java.util.concurrent.CountDownLatch;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2020-08-16
 */
public class CountDownLatchTest {

    /**
     * 主线程会被阻塞在await() 方法, 工作线程需要各自执行 countDown() 方法
     * 也就是说: 主线程等所有子线程都执行完之后(执行 countDown() 方法后) 再由主线程执行 await() 后的逻辑
     * 举个栗子 : 老师等所有同学到之后,老师给同学们讲课
     * <p>
     * 而 CyclicBarrier 是子线程各自调用 await() 方法,
     * 当调用 await() 方法的子线程数量达到初始化 CyclicBarrier 设置的 parties 后,子线程同时执行各自线程的 await() 后的逻辑
     * 举个栗子: 团队成员等所有成员到齐之后各自做各自的事情
     *
     * CountDownLatch 是计数器，只能使用一次，而 CyclicBarrier 的计数器提供 reset 功能，可以多次使用
     */
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
