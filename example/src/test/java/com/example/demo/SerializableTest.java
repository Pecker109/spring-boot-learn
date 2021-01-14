package com.example.demo;

import lombok.Data;

import java.io.*;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2020-09-15
 */
public class SerializableTest {

    /**
     * 1.Serializable 只是个标识,对象序列化的时候会判断对象的类型字符串,数组,还是实现了Serializable,都不是就会抛出序列化异常
     * 2.Serializable 只是个标识,具体的序列化操作还是在 OutputStream
     * 3.被 static 修饰的字段是不会被序列化的,因为序列化的对象的信息,而 static 属于类属性,序列化不会保存类属性
     * 4.对象中默认的 serialVersionUID = 1L ,该字段属于能否反序列化的关键因子,会判断字节流中的该字段和对象中的该字段是否一致,不一致则反序列失败
     *
     */


    @Data
    static class Student implements Serializable{
        //private static final long serialVersionUID = -209591688481019532L;
        private  String name;
        private int age;
    }

    public static void main(String[] args) {
        Student student = new Student();
        student.setAge(10);
        student.setName("张三");

        // 把对象写到文件中,序列化过程
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("xuliehua"))) {
            oos.writeObject(student);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 把文件中的信息反序列化成对象,反序列过程
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("xuliehua")))) {
            Student student1 = (Student) ois.readObject();
            System.out.println(student1);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
