package com.example.demo.spring.expand;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author Pecker
 * @Description 在读取项目中的beanDefinition之后执行，提供一个补充的扩展点
 * <p>
 * 使用场景：你可以在这里动态注册自己的beanDefinition，可以加载classpath之外的bean使用场景：你可以在这里动态注册自己的beanDefinition，可以加载classpath之外的bean
 * @since 2020-10-27
 */
//@Component
public class BeanDefinitionRegistryPostProcessorExpand1 implements BeanDefinitionRegistryPostProcessor {
	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		System.out.println("拓展点 1 - BeanDefinitionRegistryPostProcessor#postProcessBeanDefinitionRegistry()");
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("拓展点 1 - BeanDefinitionRegistryPostProcessor#postProcessBeanFactory()");
	}
}
