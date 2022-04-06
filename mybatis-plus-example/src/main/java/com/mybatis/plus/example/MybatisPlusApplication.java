package com.mybatis.plus.example;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2022-03-08
 */
@SpringBootApplication
@MapperScan("com.mybatis.plus.example.mapper")
public class MybatisPlusApplication {
    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusApplication.class, args);
        System.out.println(".................................");
        System.out.println("..........start success..........");
        System.out.println(".................................");
    }
}
