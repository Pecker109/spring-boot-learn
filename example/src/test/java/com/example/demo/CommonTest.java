package com.example.demo;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2020-10-12
 */
public class CommonTest {

    public static void main(String[] args) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap();
        map.put("A", "val-A");
        map.put("B", "val-B");
        map.put("C", "val-C");
        map.put("D", "val-D");
        System.out.println(map.get("A"));

        StringBuffer sb = new StringBuffer();
    }
}
