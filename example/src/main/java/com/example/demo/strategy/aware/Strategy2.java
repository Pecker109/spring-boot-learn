package com.example.demo.strategy.aware;

import org.springframework.stereotype.Component;

/**
 * @author Pecker
 * @Description 策略类 2
 * @since 2020-11-19
 */
@Component
public class Strategy2 implements IStrategy {
    @Override
    public StrategyEnum getStrategyType() {
        return StrategyEnum.Strategy2;
    }

    @Override
    public void doProcess() {
        System.out.println("策略类 2 实现逻辑");
    }
}
