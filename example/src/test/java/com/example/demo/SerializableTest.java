package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.*;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2020-09-15
 */
public class SerializableTest {

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

    /**
     * 1.Serializable 只是个标识,对象序列化的时候会判断对象的类型字符串,数组,还是实现了Serializable,都不是就会抛出序列化异常
     * 2.Serializable 只是个标识,具体的序列化操作还是在 OutputStream
     * 3.被 static 修饰的字段是不会被序列化的,因为序列化的对象的信息,而 static 属于类属性,序列化不会保存类属性
     * 4.对象中默认的 serialVersionUID = 1L ,该字段属于能否反序列化的关键因子,会判断字节流中的该字段和对象中的该字段是否一致,不一致则反序列失败
     * 为什么序列化?
     * 1.持久化存储：当你需要将对象的数据保存到磁盘或数据库中时，必须把对象转换成一系列字节。
     * 2.网络传输：在分布式系统中，不同的机器需要交换对象数据，序列化是实现这一点的关键。
     * 3.深拷贝：有时候需要创建对象的副本，序列化和反序列化可以帮助你实现深拷贝。
     * 更直白的说，序列化是为了实现持久化和网络传输，对象是应用层的东西，不同的语言（比如：java，go，python）创建的对象还不一样，实现持久化和网络传输的载体不认这些对象。
     */


    @Data
    static class Student implements Serializable {
        //private static final long serialVersionUID = -209591688481019532L;
        @JsonProperty(value = "userName")
        private String name;
        private int age;
    }
}
