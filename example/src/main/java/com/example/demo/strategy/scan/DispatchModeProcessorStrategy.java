package com.example.demo.strategy.scan;

import com.example.demo.annotation.StrategyKeyFlag;
import org.springframework.stereotype.Component;

/**
 * @author Pecker
 * @Description 具体的策略实现类
 * @since 2020-08-23
 */
@Component
@StrategyKeyFlag(1)
public class DispatchModeProcessorStrategy extends AbstractStrategy {
    @Override
    public int process() {
        System.out.println("执行策略 1 逻辑");
        return 1;
    }
}
