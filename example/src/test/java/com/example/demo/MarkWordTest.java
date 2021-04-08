package com.example.demo;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author Pecker
 * @Description 查看对象头
 * @since 2021-03-15
 */
public class MarkWordTest {
    public static void main(String[] args) {
        System.out.println(ClassLayout.parseInstance(String.class));
    }
}
