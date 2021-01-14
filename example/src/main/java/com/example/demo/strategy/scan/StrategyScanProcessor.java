package com.example.demo.strategy.scan;

import com.example.demo.annotation.StrategyKeyFlag;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Pecker
 * @Description 项目启动扫描策略类到 Spring 容器
 * @since 2020-08-23
 */
@Component
@SuppressWarnings({"unuse", "rawtypes"})
public class StrategyScanProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Map<Integer, Class> map = new HashMap<Integer, Class>();

        //标记策略 key 注解的类所在的包
        String basePackage = "com.example.demo.strategy.scan";
        MyClassScanner.scan(basePackage, StrategyKeyFlag.class).forEach(x -> {
            int type = x.getAnnotation(StrategyKeyFlag.class).value();
            map.put(type, x);
        });
        beanFactory.registerSingleton(StrategyKeyFlag.class.getName(), map);

    }
}
