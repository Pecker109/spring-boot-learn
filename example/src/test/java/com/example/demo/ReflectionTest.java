package com.example.demo;

import com.example.demo.model.Student;
import lombok.SneakyThrows;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author Pecker
 * @Description 反射
 * @since 2020-12-02
 */
public class ReflectionTest {

    @SneakyThrows
    public static void main(String[] args) {
        System.out.println(Student.class.getName());
        for (Method method : Student.class.getMethods()) {
            System.out.println(method.getName());
        }

        //Class  --> Constructor --> Method  --> Field

        //Class  : 获取类的 Class 对象实例
        Class<?> clz = Class.forName(Student.class.getName());

        //Constructor:  根据 Class 对象实例获取 Constructor 对象
        Constructor<?> constructor = clz.getConstructor();

        //使用 Constructor 对象的 newInstance 方法获取反射类对象
        Object object = constructor.newInstance();

        //Method
        //获取方法的 Method 对象,getMethod()获取该类所有 public 访问权限的方法，包括从父类继承的
        //Method method = clz.getMethod("ageAdd", int.class);
        //getDeclaredMethods() 方法获取的都是该类的方法，不包括父类方法,不问访问权限,包括private 方法
        Method method = clz.getDeclaredMethod("ageAdd", int.class);
        //当前方法访问权限是“private”的,获取私有方法的访问权限，如果不加会报异常 IllegalAccessException
        method.setAccessible(true);
        //利用 invoke 方法调用方法
        int newAge = (int) method.invoke(object, 4);
        System.out.println(newAge);

        //Field : final 修饰的字段不初始化,赋值放在构造函数中,后续也是可以更改 final 修饰的值￿￿
    }
}
