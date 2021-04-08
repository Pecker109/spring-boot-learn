package com.example.demo;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;

import java.time.Duration;
import java.time.Instant;

/**
 * @author Pecker
 * @Description 计算过去的时间
 * @since 2021-04-06
 */
@Slf4j
public class MeasureElapsedTimeTest {


    public static void main(String[] args) {
        currentTimeMillisTest();

        nanoTimeTest();

        InstantTest();

        StopWatchTest();
    }

    @SneakyThrows
    static void currentTimeMillisTest() {
        System.out.println("currentTimeMillis 计算时间...");
        long start = System.currentTimeMillis();
        Thread.sleep(5000);
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println(timeElapsed);
    }

    @SneakyThrows
    static void nanoTimeTest() {
        System.out.println("nanoTime 计算时间...");
        long start = System.nanoTime();
        Thread.sleep(5000);
        long finish = System.nanoTime();
        long timeElapsed = finish - start;
        System.out.println(timeElapsed);
    }

    @SneakyThrows
    static void InstantTest() {
        System.out.println("Instant 计算时间...");
        Instant start = Instant.now();
        Thread.sleep(5000);
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        System.out.println(timeElapsed);
    }

    @SneakyThrows
    static void StopWatchTest() {
        System.out.println("StopWatch 计算时间...");
        StopWatch watch = new StopWatch();
        watch.start();
        Thread.sleep(5000);
        watch.stop();
        System.out.println("Time Elapsed: " + watch.getTime());
    }

}
