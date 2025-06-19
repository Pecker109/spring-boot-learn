package com.example.demo.spring;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;

public class BeanFactoryPostProcessorTest implements BeanFactoryPostProcessor {
    /**
     * 在 Bean 初始化之前,可以获取到 BeanDefinition ,并对 BeanDefinition 做修改.
     * 甚至可以直接初始化 Bean
     * Spring 内置 bean应用场景:
     * - ConfigurationClassPostProcessor增强 @Configuration 标识的类,创建 cglib 代理
     * {@link ConfigurationClassPostProcessor#postProcessBeanFactory(ConfigurableListableBeanFactory)}-->{@link ConfigurationClassPostProcessor#enhanceConfigurationClasses(ConfigurableListableBeanFactory)}
     * - PropertySourcesPlaceholderConfigurer 解析 ${...} 占位符为实际值。
     *
     * @param beanFactory Spring上下文容器
     * @throws BeansException e
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}
