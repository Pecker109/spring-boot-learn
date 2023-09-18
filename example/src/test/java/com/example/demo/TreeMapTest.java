package com.example.demo;


import java.util.TreeMap;

public class TreeMapTest {

    public static void main(String[] args) {
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        treeMap.put(3, "3");
        treeMap.put(1, "1");
        treeMap.put(2, "2");
        treeMap.forEach((key, value) -> {
            System.out.println(key);
        });
    }


}
