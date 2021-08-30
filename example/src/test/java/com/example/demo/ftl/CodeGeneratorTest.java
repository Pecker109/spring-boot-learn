package com.example.demo.ftl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2021-08-24
 */
public class CodeGeneratorTest {
    public static void main(String[] args) throws IOException, TemplateException {
        Map<String, Object> objectMap = new HashMap<>();
        //定义包路径
        objectMap.put("package", "com.example.demo.ftl");
        //定义实体类
        objectMap.put("classname", "Student");

        //定义实体类属性
        List<Map<String, Object>> columns = new ArrayList<>();
        //姓名字段
        Map<String, Object> column1 = new HashMap<>();
        column1.put("propertyType", "String");
        column1.put("propertyName", "name");
        column1.put("comment", "姓名");
        columns.add(column1);
        //年龄字段
        Map<String, Object> column2 = new HashMap<>();
        column2.put("propertyType", "Integer");
        column2.put("propertyName", "age");
        column2.put("comment", "年龄");
        columns.add(column2);

        //定义类的属性
        objectMap.put("columns", columns);
        //定义作者
        objectMap.put("author", "张三");
        //定义创建时间
        objectMap.put("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        //定义类描述
        objectMap.put("tableComment", "学生信息");

        //生产目标代码
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
        configuration.setDefaultEncoding(Charset.forName("UTF-8").name());
        configuration.setClassForTemplateLoading(CodeGeneratorTest.class, "/");
        Template template = configuration.getTemplate("/templates/entity.java.ftl");
        FileOutputStream fileOutputStream = new FileOutputStream(new File("/Users/apple/Documents/project/spring-boot-learn/example/src/test/java/com/example/demo/ftl/Student.java"));
        template.process(objectMap, new OutputStreamWriter(fileOutputStream, Charset.forName("UTF-8").name()));
        fileOutputStream.close();
        System.out.println("文件创建成功");

    }
}
