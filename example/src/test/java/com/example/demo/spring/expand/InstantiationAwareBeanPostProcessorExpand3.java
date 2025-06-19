package com.example.demo.spring.expand;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;

/**
 * @author Pecker
 * @Description 该接口继承了BeanPostProcess接口，区别如下：
 *
 * BeanPostProcess接口只在bean的初始化阶段进行扩展（注入spring上下文前后），
 * 而InstantiationAwareBeanPostProcessor接口在此基础上增加了3个方法，把可扩展的范围增加了实例化阶段和属性注入阶段。
 * - postProcessBeforeInstantiation：实例化bean之前，相当于new这个bean之前
 * - postProcessAfterInstantiation：实例化bean之后，相当于new这个bean之后
 * - postProcessPropertyValues：bean已经实例化完成，在属性注入时阶段触发，@Autowired,@Resource等注解原理基于此方法实现
 * - postProcessBeforeInitialization：初始化bean之前，相当于把bean注入spring上下文之前
 * - postProcessAfterInitialization：初始化bean之后，相当于把bean注入spring上下文之后
 * 这个扩展点非常有用 ，无论是写中间件和业务中，都能利用这个特性。比如对实现了某一类接口的bean在各个生命期间进行收集，或者对某个类型的bean进行统一的设值等等。
 * @since 2020-10-27
 */
//@Component
public class InstantiationAwareBeanPostProcessorExpand3 implements InstantiationAwareBeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("拓展点 3 - InstantiationAwareBeanPostProcessor#postProcessBeforeInitialization,初始化bean之前，相当于把bean注入spring上下文之前");
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("拓展点 3 - InstantiationAwareBeanPostProcessor#postProcessAfterInitialization,初始化bean之后，相当于把bean注入spring上下文之后");
		return bean;
	}
	@Override
	public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
		System.out.println("拓展点 3 - InstantiationAwareBeanPostProcessor#postProcessProperties(),bean已经实例化完成，在属性注入时阶段触发");
		return pvs;
	}
}
