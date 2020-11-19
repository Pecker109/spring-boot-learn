package com.example.demo.bean.strategy;

/**
 * @author Pecker
 * @Description 策略接口
 * @since 2020-11-19
 */
public interface IStrategy {

    /**
     * 获取实现类的策略类型,策略实现类和策略类型一一对应
     *
     * @return 策略类型
     */
    StrategyEnum getStrategyType();

    /**
     * 策略类的具体实现逻辑
     */
    void doProcess();

}
