package com.example.demo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Predicate;

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

