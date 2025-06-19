package com.example.demo;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.CRC32;

public class ListTest {
    public static void main(String[] args) {
        List<String> stringList = Arrays.asList("a", "b", "c");

        List<String> list = Stream.of("a", "b", "c").collect(Collectors.toList());

        List<String> copyList = Collections.nCopies(3, "a");
        System.out.println(copyList);
    }

}
