package com.example.demo;

import com.example.demo.model.Student;

/**
 * @author Pecker
 * @Description 引用传递, 值传递
 * @since 2021-08-04
 */
public class ReferencePassingTest {

    public static void main(String[] args) {

        /*
         * 在方法体里对参数重新赋值，会影响传入的引用类型参数，而不会影响基本类型参数，
         * 因为基本类型参数是值传递，而引用类型参数是引用传递。
         */

        int x = 10;
        System.out.println("调用前x的值：" + x);
        updateValue(x);
        System.out.println("调用后x的值：" + x);

        String str = "message";
        System.out.println("调用前 String 的值：" + str);
        updateString(str);
        System.out.println("调用后 String 的值：" + str);

        Student student = new Student("小明", 20);
        System.out.println("调用前student的值：" + student.toString());
        updateStudent(student);
        System.out.println("调用后student的值：" + student.toString());
    }

    public static void updateValue(int value) {
        value = 3 * value;
    }

    public static void updateString(String value) {
        value = value + "=====";
    }

    public static void updateStudent(Student student) {
        student.setName("xiao ming");
        student.setAge(18);
    }
}
