package com.example.demo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2020-08-21
 */
public class AtomicTest {

    public static void main(String[] args) {

        AtomicInteger atomic = new AtomicInteger();

        // CAS 更新值
        boolean compareAndSet = atomic.compareAndSet(0, 1);
        System.out.println(compareAndSet);

        // 先加 1 再get
        System.out.println(atomic.incrementAndGet());
        System.out.println(atomic.get());

        // 先 get 原来的值再加 1
        System.out.println(atomic.getAndIncrement());
        System.out.println(atomic.get());

        // 先 get 原来的值再减 1
        System.out.println(atomic.getAndDecrement());
        System.out.println(atomic.get());
    }


}
