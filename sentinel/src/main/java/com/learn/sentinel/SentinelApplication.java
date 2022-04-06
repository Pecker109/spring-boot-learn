package com.learn.sentinel;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2021-11-28
 */
@SpringBootApplication
public class SentinelApplication {
    public static void main(String[] args) {
        SpringApplication.run(SentinelApplication.class, args);
        System.out.println(".................................");
        System.out.println("..........start success..........");
        System.out.println(".................................");
    }
}
