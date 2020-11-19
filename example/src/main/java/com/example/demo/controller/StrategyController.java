package com.example.demo.controller;

import com.example.demo.bean.HandlerContext;
import com.example.demo.bean.strategy.StrategyConfig;
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

    @Autowired
    StrategyConfig strategyConfig;

    @GetMapping("/strategy")
    public String index() {
        System.out.println(handlerContext.getInstance(1));
        return "策略模式执行成功";
    }

    @GetMapping("/ApplicationContextAware/strategy")
    public String strategy1() {
        strategyConfig.getStrategy(1).doProcess();
        return "策略模式执行成功";
    }
}
