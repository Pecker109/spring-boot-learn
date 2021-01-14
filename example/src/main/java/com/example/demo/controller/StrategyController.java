package com.example.demo.controller;

import com.example.demo.strategy.aware.StrategyConfig;
import com.example.demo.strategy.scan.StrategyContext;
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
    StrategyContext strategyContext;

    @Autowired
    StrategyConfig strategyConfig;

    @GetMapping("/strategy")
    public String index() {
        //自定义包扫描,扫描策略类存入 map 中
        strategyContext.getInstance(1).process();
        return "策略模式执行成功";
    }

    @GetMapping("/ApplicationContextAware/strategy")
    public String strategy1() {
        //实现 ApplicationContextAware ,扫描策略类存入 map 中
        strategyConfig.getStrategy(1).doProcess();
        return "策略模式执行成功";
    }
}
