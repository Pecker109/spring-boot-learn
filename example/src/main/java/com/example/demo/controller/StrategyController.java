package com.example.demo.controller;

import com.example.demo.bean.HandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Pecker
 * @Description 策略模式 Controller
 * @since 2020-08-23
 */
@RestController
public class StrategyController {

    @Autowired
    HandlerContext handlerContext;

    @GetMapping("/strategy")
    public String index() {
        System.out.println(handlerContext.getInstance(1));
        return "策略模式执行成功";
    }
}
