package com.example.demo.strategy.scan;

import com.example.demo.annotation.StrategyKeyFlag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Pecker
 * @Description 根据类型实例化抽象类
 * @since 2020-08-23
 */
@Component
public class StrategyContext {
    @Autowired
    private ApplicationContext beanFactory;

    public AbstractStrategy getInstance(Integer type) {

        Map<Integer, Class> map = (Map<Integer, Class>) beanFactory.getBean(StrategyKeyFlag.class.getName());

        return (AbstractStrategy) beanFactory.getBean(map.get(type));
    }
}
