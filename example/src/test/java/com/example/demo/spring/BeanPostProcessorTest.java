package com.example.demo.spring;

import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.InitDestroyAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.*;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;

public class BeanPostProcessorTest implements BeanPostProcessor {

    /*
	1.BeanPostProcessor 的作用与意义
		BeanPostProcessor 是 Spring 框架的核心扩展接口之一，允许开发者在 Bean 的初始化阶段前后插入自定义逻辑，实现对 Bean 实例的增强或修改。它在 Spring 容器的生命周期中扮演重要角色，是 AOP、依赖注入、注解处理等功能的基础。
	2.作用
		-- 修改 Bean 属性：在 Bean 初始化前后调整属性值。
		-- 生成代理对象：为 Bean 创建动态代理（如 AOP 代理）。
		-- 注解处理：解析和处理 Bean 上的注解（如 @Autowired、@PostConstruct）。
		-- 生命周期干预：自定义初始化逻辑或资源释放。
	3.执行时机
		1. Bean 实例化（构造函数调用）: doCreateBean-1
		   ↓
		2. 属性注入（依赖注入，如 `@Autowired`）:doCreateBean-3
		   ↓
		3. BeanPostProcessor.postProcessBeforeInitialization():doCreateBean-4
		   ↓
		4. 初始化方法执行（`InitializingBean.afterPropertiesSet()` 或 `init-method`）:doCreateBean-4
		   ↓
		5. BeanPostProcessor.postProcessAfterInitialization():doCreateBean-4
		   ↓
		6. Bean 就绪，加入容器
	 */

    /**
     * bean 初始化之前回调处理
     * 调用时机发生在执行 doCreateBean4-执行生命周期回调时
     * Spring 内置 bean应用场景:
     * - {@link InitDestroyAnnotationBeanPostProcessor#postProcessBeforeInitialization(Object, String)}解析 @PostConstruct @PreDestroy 注解,执行标注的方法
     * - { @link ApplicationContextAwareProcessor#postProcessBeforeInitialization(Object, String)}: 执行各种Aware接口回调 invokeAwareInterfaces():
     * ---- {@link EnvironmentAware#setEnvironment(Environment)}
     * ---- {@link ResourceLoaderAware#setResourceLoader(ResourceLoader)}
     * ---- {@link ApplicationEventPublisherAware#setApplicationEventPublisher(ApplicationEventPublisher)}
     * ---- {@link ApplicationContextAware#setApplicationContext(ApplicationContext)} )}
     *
     * @param bean     the new bean instance
     * @param beanName the name of the bean
     * @return 要使用的bean实例，可能是原始的 bean,可能是封装过的;
     * if {@code null}, no subsequent BeanPostProcessors will be invoked
     * @throws org.springframework.beans.BeansException in case of errors
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * bean 初始化之后的回调处理
     * 调用时机发生在执行 doCreateBean4-执行生命周期回调时:
     * Spring 内置 bean应用场景:
     * -{@link AbstractAutoProxyCreator#postProcessAfterInitialization(Object, String)}:创建 AOP 动态代理
     *
     * @param bean     the new bean instance
     * @param beanName the name of the bean
     * @return 要使用的bean实例，可能是原始的 bean,可能是封装过的;
     * if {@code null}, no subsequent BeanPostProcessors will be invoked
     * @throws org.springframework.beans.BeansException in case of errors
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet
     * @see org.springframework.beans.factory.FactoryBean
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

}
