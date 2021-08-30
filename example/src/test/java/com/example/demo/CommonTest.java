package com.example.demo;

import com.example.demo.model.Student;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2020-10-12
 */
public class CommonTest {


    @SneakyThrows
    public static void main(String[] args) {

        // 如何把list集合拼接成以逗号分隔的字符串 a,b,c
        List<String> list = Arrays.asList("a", "b", "c");
        // 第一种方法，可以用stream流
        String join = String.join(",", list);
        System.out.println(join);


        Student student = new Student();
        BeanUtils.setProperty(student, "name", "zhangsan");
        BeanUtils.setProperty(student, "age", 18);
        System.out.println(BeanUtils.getProperty(student, "name"));
        System.out.println(student);

        // 对象转 map
        Map<String, String> map = BeanUtils.describe(student);
        System.out.println(map);

        // map 转对象
        Student newStudent = new Student();
        BeanUtils.populate(newStudent, map);

    }

}
