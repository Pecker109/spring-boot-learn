package com.example.demo;

import com.example.demo.model.Student;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2021-04-21
 */
public class HashCodeTest {

    /**
     * 1.hashCode() 和 equals() 方法都是 Object 类提供的方法
     * 2. Object#equals() 方法是根据 == 来判断的, == 比较封装类型比较的是两个对象的内存地址
     * 3. Object#hashCode() 方法是 native 方法,返回的结果是对象的内存地址
     * 4.正常情况下应该满足: equals()相等的情况下 hashCode() 一定相等
     * 5.所以重写 equals() 方法要重写 hashCode()
     * <p>
     * 下面测试的 Student 类没有没有重写equals()和hashCode(),
     * equals() 比较的是两个对象的内存地址,两个对象的内存地址肯定是不同的
     * hashCode()返回的就是内存地址
     * <p>
     * 可以把 Student 类加上@Data 注解,再进行测试,@Data 会帮我们重写equals()和hashCode()
     * 再来看下面的执行结果equals()和hashCode() 结果就相等了
     */
    public static void main(String[] args) {
        Student student1 = new Student();
        student1.setName("张三");
        student1.setAge(10);
        Student student2 = new Student();
        student2.setName("张三");
        student2.setAge(10);

        System.out.println(student1 == student2);
        System.out.println(student1.equals(student2));
        System.out.println(student1.hashCode());
        System.out.println(student2.hashCode());
    }

}
