package com.example.demo;

import com.example.demo.model.Student;
import lombok.SneakyThrows;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * 反射就是在运行时才知道要操作的类是什么，并且可以在运行时获取类的完整构造，并调用对应的方法。
 * 反射出来的字节码文件,是加载在内存中的
 *
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


        /**
         *
         * Method#invoke()  --> MethodAccessor#invoke()
         * MethodAccessor 实现有两个版本，一个是 Native 版本，一个是 Java 版本
         * Native 版本 NativeMethodAccessorImpl , 父类是 DelegatingMethodAccessorImpl
         * Java 版本 DelegatingMethodAccessorImpl , 会 set 一个委托对象,这个委托对象就是 NativeMethodAccessorImpl
         * Native 版本一开始启动快，但是随着运行时间边长，速度变慢。Java 版本一开始加载慢，但是随着运行时间边长，速度变快。
         * 正是因为两种存在这些问题，所以第一次加载的时候我们会发现使用的是 NativeMethodAccessorImpl 的实现，
         * 而当反射调用次数超过 15 次之后，则使用 MethodAccessorGenerator 生成的 MethodAccessorImpl 对象去实现反射。
         *
         */
        int newAge = (int) method.invoke(object, 4);
        System.out.println(newAge);

        //Field : final 修饰的字段不初始化,赋值放在构造函数中,后续也是可以更改 final 修饰的值￿￿
    }
}
