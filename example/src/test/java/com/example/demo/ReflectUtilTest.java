package com.example.demo;


import com.example.demo.model.Student;
import com.example.demo.util.ReflectUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * 自己实现的 ReflectUtil 工具类测试
 *
 * @author Pecker
 * @Description 反射
 * @since 2021-06-10
 */
public class ReflectUtilTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @SneakyThrows
    public static void main(String[] args) {
        toUpper();
    }

    /**
     * 新增字段测试
     */
    static void addField() throws JsonProcessingException, InvocationTargetException, IllegalAccessException {
        Student student = new Student();
        student.setName("name123");
        System.out.println("Student：" + MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(student));

        Map<String, Object> propertiesMap = new HashMap<>(1);
        propertiesMap.put("hobby", "English");

        Student obj = (Student) ReflectUtil.getObject(student, propertiesMap);

        System.err.println("动态为Student添加class字段之后，Student：" + MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj));
    }


    static void toUpper() throws JsonProcessingException, InvocationTargetException, IllegalAccessException {
        Student student = new Student();
        student.setName("name123");
        student.setAge(101);

        System.out.println("Student：" + MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(student));

        Object obj = ReflectUtil.firstWordToUpper(student);

        System.err.println("字段转大写，Student：" + MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj));
    }
}
