package com.example.demo.multiThread;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2020-08-28
 */
public class ThreadPoolTest {
    @SneakyThrows
    public static void main(String[] args) {

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

