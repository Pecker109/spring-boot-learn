package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
/**
 * @author Pecker
 * @Description 类信息
 * @since 2020-08-21
 */
public class AtomicTest {

    public static void main(String[] args) {
        new HashMap<>();
        new ConcurrentHashMap<>();
        new ArrayList<>();
        new CopyOnWriteArrayList<>();


    }


}
