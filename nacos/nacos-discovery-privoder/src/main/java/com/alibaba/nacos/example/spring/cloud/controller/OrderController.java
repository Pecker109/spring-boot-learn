package com.alibaba.nacos.example.spring.cloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@Slf4j
@RestController
@RequestMapping("/provider/order")
public class OrderController {

    @RequestMapping("/findOrderByUserId/{userId}")
    public String findOrderByUserId(@PathVariable("userId") Integer userId) {
        return String.valueOf(userId);
    }

}
