package com.example.demo;


import java.util.function.Predicate;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2020-08-28
 */
public class ThreadPoolTest {
    public static void main(String[] args) {
        //ReentrantLock reentrantLock = new ReentrantLock();
        Predicate<String> predicate = s -> s.length() > 0;
        predicate.test("");

    }

}

