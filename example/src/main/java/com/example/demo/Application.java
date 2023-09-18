package com.example.demo;

import cn.hutool.core.thread.NamedThreadFactory;
import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2020-04-23
 */
@EnableScheduling
@SpringBootApplication
// @ComponentScan(excludeFilters = {@ComponentScan.Filter(type = FilterType.REGEX,pattern = "com.example.demo.config.WebAppConfig") })
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println(".................................");
        System.out.println("..........start success..........");
        System.out.println(".................................");
    }

}
