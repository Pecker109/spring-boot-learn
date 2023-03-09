package com.example.demo.multiThread;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.util.concurrent.*;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2020-08-28
 */
public class ThreadPoolTest {
    @SneakyThrows
    public static void main(String[] args) {

        test1();
        //singlePoll();
    }

    @SneakyThrows
    private static void test1() {
        ExecutorService pool = Executors.newCachedThreadPool();

        int total = 10;
        int begin = 0;
        int interval = 1;
        do {
            pool.execute(() -> System.out.println("doWorker..."));
            begin = pool.submit(new ThreadPoolTest.SelectTask(begin, interval)).get();
            //System.out.println("begin...." + begin);
        } while (begin <= total);
    }

    /**
     * 单线程线程池只有等当前线程的当前任务执行完之后才会执行下一个任务
     */
    private static void singlePoll() {
        ThreadPoolExecutor singlePool = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),
                r -> new Thread(r, "single_poll"));

        singlePool.execute(new TaskExecutor("A"));
        singlePool.execute(new TaskExecutor("B"));
    }

    static class TaskExecutor extends Thread {
        String name;

        public TaskExecutor(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            while (true) {
                System.out.println(name);
            }
        }
    }

    @SneakyThrows
    private void shutdownTest() {

        ExecutorService threadPool = Executors.newCachedThreadPool();
        try {
            for (int i = 0; i < 1000; i++) {
                threadPool.execute(() -> {
                    try {
                        System.out.println("线程开始执行...");
                        //Thread.sleep(50000L);
                        System.out.println("线程执行结束");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }

        } finally {
            System.out.println("关闭线程池...");
            //threadPool.shutdown();
            //threadPool.awaitTermination(5, TimeUnit.SECONDS);
            System.out.println("线程池关闭!!!");
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SelectTask implements Callable<Integer> {
        int begin;
        int interval;

        @Override
        public Integer call() throws Exception {
            begin += interval;
            int currentBegin = begin - interval;
            System.out.println("begin...." + currentBegin);
            System.out.println(Thread.currentThread());
            return begin;
        }
    }

}

