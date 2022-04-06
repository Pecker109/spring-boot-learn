package com.alibaba.nacos.example.spring.cloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequestMapping("/config")
public class ConfigController {

    @Value("${user.name}")
    private String name;

    @Value("${user.age:12}")
    private Integer age;

    @RequestMapping("/get")
    public String get() {
        return name.concat(":").concat(String.valueOf(age));
    }
}