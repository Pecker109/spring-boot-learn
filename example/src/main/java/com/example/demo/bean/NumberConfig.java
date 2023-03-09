package com.example.demo.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
//@Component
//@PropertySource("classpath:/cfg/application.yml")
//@ConfigurationProperties(prefix="register")
public class NumberConfig {

private List<NumberInfo> numberInfo =new ArrayList<>();


}