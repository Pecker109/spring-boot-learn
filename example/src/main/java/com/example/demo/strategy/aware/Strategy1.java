package com.example.demo.strategy.aware;

import org.springframework.stereotype.Component;

/**
 * @author Pecker
 * @Description 策略类 1
 * @since 2020-11-19
 */
@Component
public class Strategy1 implements IStrategy {
    @Override
    public StrategyEnum getStrategyType() {
        return StrategyEnum.Strategy1;
    }

    @Override
    public void doProcess() {
        System.out.println("策略类 1 实现逻辑");
    }
}
