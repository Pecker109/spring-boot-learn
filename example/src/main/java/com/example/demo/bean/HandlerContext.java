package com.example.demo.bean;

import com.example.demo.annotation.StrategyKeyFlag;
import com.example.demo.handler.AbstractHandler;
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
public class HandlerContext {
    @Autowired
    private ApplicationContext beanFactory;

    public AbstractHandler getInstance(Integer type) {

        Map<Integer, Class> map = (Map<Integer, Class>) beanFactory.getBean(StrategyKeyFlag.class.getName());

        return (AbstractHandler) beanFactory.getBean(map.get(type));
    }
}
