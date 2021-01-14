package com.example.demo;

import java.io.FileInputStream;
import java.lang.reflect.Method;

/**
 * @author Pecker
 * @Description 自定义类加载器
 * @since 2020-12-28
 */
public class MyClassLoaderTest {

    static class MyClassLoader extends ClassLoader {
        private String classPath;

        public MyClassLoader(String classPath) {
            this.classPath = classPath;
        }

        private byte[] loadByte(String name) throws Exception {
            name = name.replaceAll("\\.", "/");
            FileInputStream fis = new FileInputStream(classPath + "/" + name
                    + ".class");
            int len = fis.available();
            byte[] data = new byte[len];
            fis.read(data);
            fis.close();
            return data;
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            try {
                byte[] data = loadByte(name);
                //defineClass将一个字节数组转为Class对象，这个字节数组是class文件读取后最终的字节 数组。
                return defineClass(name, data, 0, data.length);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ClassNotFoundException();
            }
        }

        public static void main(String args[]) throws Exception {
            //初始化自定义类加载器，会先初始化父类ClassLoader，其中会把自定义类加载器的父加载器设置为应用程序类加载器AppClassLoader
            MyClassLoader classLoader = new MyClassLoader(" ~/Documents/project/spring-boot-learn");
            Class clazz = classLoader.loadClass("com.example.demo.model.Student");
            Object obj = clazz.newInstance();
            Method method = clazz.getDeclaredMethod("ageAdd", Integer.TYPE);
            method.invoke(obj, 1);
            //当你的 Target 目录下有Student 类的时候,类加载器就是AppClassLoader,没有 Student 类的时候就是
            System.out.println(clazz.getClassLoader().getClass().getName());
        }

    }
}
