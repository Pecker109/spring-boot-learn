package com.example.demo;

import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2021-02-01
 */
public class PropertiesTest {


    /*
    在主目录下建properties-test.xml 文件

    <?xml version="1.0" encoding="UTF-8"?>
    <!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd">
    <properties>
    <entry key="name"> 张三 </entry>
    </properties>
     */
    @SneakyThrows
    public static void main(String[] args) {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("properties-test.xml");
        prop.loadFromXML(fis);
        prop.list(System.out);
        System.out.println("\nThe foo property: " + prop.getProperty("foo"));
    }
}

