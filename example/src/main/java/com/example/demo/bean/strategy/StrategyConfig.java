package com.example.demo.bean.strategy;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Pecker
 * @Description 将策略类放到 map 中
 * @since 2020-11-19
 */
@Component
public class StrategyConfig implements ApplicationContextAware {

    /**
     * 策略类Map
     */
    private Map<Integer, IStrategy> strategyMap;

    /**
     * 根据策略类型获取对应的策略Bean
     *
     * @param type
     * @return
     */
    public IStrategy getStrategy(int type) {
        return strategyMap.get(type);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, IStrategy> beanMap = applicationContext.getBeansOfType(IStrategy.class);
        strategyMap = beanMap.entrySet()
                .stream()
                .collect(Collectors.toMap(x -> x.getValue().getStrategyType().getType(), Map.Entry::getValue));

        System.out.println("初始化策略集完成");
    }
}
