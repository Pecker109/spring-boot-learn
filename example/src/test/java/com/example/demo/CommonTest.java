package com.example.demo;

import com.example.demo.controller.StrategyController;
import com.example.demo.strategy.aware.IStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2020-10-12
 */
@Component
public class CommonTest {

    @Qualifier("strategy1")
    @Autowired
    IStrategy iStrategy;

    @Resource
    StrategyController strategyController;


}
