package com.example.demo.handler.impl;

import com.example.demo.annotation.StrategyKeyFlag;
import com.example.demo.handler.AbstractHandler;
import org.springframework.stereotype.Component;

/**
 * @author Pecker
 * @Description 具体的策略实现类
 * @since 2020-08-23
 */
@Component
@StrategyKeyFlag(1)
public class DispatchModeProcessor extends AbstractHandler {
    @Override
    public int handle() {
        System.out.println("执行策略 1 逻辑");
        return 1;
    }
}
