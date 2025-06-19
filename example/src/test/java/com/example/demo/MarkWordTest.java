package com.example.demo;

import com.example.demo.model.Student;
import org.openjdk.jol.info.ClassLayout;

/**
 * @author Pecker
 * @Description 查看对象头
 * @since 2021-03-15
 */
public class MarkWordTest {
    public static void main(String[] args) {
        System.out.println(ClassLayout.parseInstance(String.class).toPrintable());
        System.out.println();
        System.out.println(ClassLayout.parseInstance(new int[]{}).toPrintable());
        System.out.println();
        System.out.println(ClassLayout.parseInstance(new Student()).toPrintable());
    }
}
