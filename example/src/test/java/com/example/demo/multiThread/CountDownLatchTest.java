package com.example.demo.multiThread;

import lombok.SneakyThrows;
import org.springframework.core.task.TaskExecutor;

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
     * <p>
     * CountDownLatch 是计数器，只能使用一次，而 CyclicBarrier 的计数器提供 reset 功能，可以多次使用
     */
    public static void main(String[] args) throws InterruptedException {
       //test1();
       test2();
    }


    @SneakyThrows
    static void test1(){
        /**
         * 某一线程在开始运行前等待 n 个线程执行完毕。
         * 将 CountDownLatch 的计数器初始化为 n ：new CountDownLatch(n)，
         * 每当一个任务线程执行完毕，就将计数器减 1 countdownlatch.countDown()，
         * 当计数器的值变为 0 时，在CountDownLatch上 await() 的线程就会被唤醒。
         * 一个典型应用场景就是启动一个服务时，主线程需要等待多个组件加载完毕，之后再继续执行。
         */
        CountDownLatch latch = new CountDownLatch(10);

        for (int i = 0; i < 10; i++) {
            System.out.println("foreach" + i);
            TaskExecutor task = new TaskExecutor(latch);
            task.start();
        }
        System.out.println("latch wait");
        // 在这里阻塞,等待所有任务执行完之后才能往下执行
        latch.await();
        System.out.println("latch over");
    }


    @SneakyThrows
    static void test2(){
        /**
         * 实现多个线程开始执行任务的最大并行性。
         * 注意是并行性，不是并发，强调的是多个线程在某一时刻同时开始执行。
         * 类似于赛跑，将多个线程放到起点，等待发令枪响，然后同时开跑。
         * 做法是初始化一个共享的 CountDownLatch 对象，将其计数器初始化为 1 ：new CountDownLatch(1)，
         * 多个线程在开始执行任务前首先 coundownlatch.await()，
         * 当主线程调用 countDown() 时，计数器变为 0，多个线程同时被唤醒。
         */
        CountDownLatch latch = new CountDownLatch(1);

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    latch.await();
                    System.out.println(finalI);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        System.out.println("预备...");
        Thread.sleep(2000);
        System.out.println("跑...");
        latch.countDown();


        Thread.sleep(5000);
        System.out.println("over");
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
