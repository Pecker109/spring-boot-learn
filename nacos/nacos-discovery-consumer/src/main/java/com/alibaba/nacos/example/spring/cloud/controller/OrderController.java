package com.alibaba.nacos.example.spring.cloud.controller;


import com.alibaba.nacos.example.spring.cloud.feign.OrderFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/consumer/order")
public class OrderController {

    @Autowired
    OrderFeignService orderFeignService;

    @RequestMapping("/findOrderByUserId/{userId}")
    public String findOrderByUserId(@PathVariable("userId") Integer userId) {
        return orderFeignService.findOrderByUserId(userId);
    }
}
