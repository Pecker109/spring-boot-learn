package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2020-04-23
 */
@SpringBootApplication
//@ComponentScan(excludeFilters = {@ComponentScan.Filter(type = FilterType.REGEX,pattern = "com.example.demo.config.WebAppConfig") })
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println(".................................");
        System.out.println("..........start success..........");
        System.out.println(".................................");
    }

}
