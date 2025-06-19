package com.example.demo.spring;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.aop.framework.autoproxy.InfrastructureAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.annotation.SpringTransactionAnnotationParser;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;

import java.lang.reflect.Method;

public class SpringTransactionTest {

    /**
     * 主线 1:自动注册到Spring 容器,并且为 @Transactional 标注的类或方法创建动态代理
     * 自动装配类 {@link TransactionAutoConfiguration}
     * 事务后置处理器{@link InfrastructureAdvisorAutoProxyCreator}
     * 继承{@link AbstractAutoProxyCreator#postProcessAfterInitialization(Object, String)} 来创建动态代理
     * {@link AbstractAdvisorAutoProxyCreator#getAdvicesAndAdvisorsForBean(Class, String, TargetSource)}: 获取指定 bean 可用的织入对象-Advisor
     * -->{@link AnnotationTransactionAttributeSource#findTransactionAttribute(Method)} 解析@Transaction 注解,将事务属性封装成 TransactionAttribute 对象
     * -->{@link SpringTransactionAnnotationParser#parseTransactionAnnotation(AnnotationAttributes)}
     */
    public static void registry() {
        /*
        1.自动注入: spring-boot-autoconfigure 包中的 spring.factories 文件中装配了自动配置类 TransactionAutoConfiguration
                @EnableTransactionManagement 开启事务管理器
                    ->@Import({TransactionManagementConfigurationSelector.class}) 向 Spring 容器中注入两个类
                    AutoProxyRegistrar : 给容器中注册一个 InfrastructureAdvisorAutoProxyCreator 组件；利用后置处理器机制在对象创建以后，包装对象，返回一个代理对象（增强器），代理对象执行方法利用拦截器链进行调用；
                    ProxyTransactionManagementConfiguration: 配置类，定义了事务增强器。

        2.创建动态代理: InfrastructureAdvisorAutoProxyCreator 是个 BeanPostProcessor ,
	            BeanPostProcessor的执行时机在 doCreateBean4()-执行声明周期回调时触发,这时Spring会对 AOP 创建创建动态代理(可查看BeanPostProcessor 具体执行时机).
            AbstractAutoProxyCreator#postProcessAfterInitialization() 创建动态代理
                ->postProcessAfterInitialization()->wrapIfNecessary()->getAdvicesAndAdvisorsForBean():创建动态代理时会校验该 bean 是否需要被创建事务的动态代理
                    校验方法为:解析 @Transaction 注解,
		 	            最终调用栈:AnnotationTransactionAttributeSource#findTransactionAttribute()
         */
    }


    /**
     * 主线 2: Spring 事务调用流程
     * Spring AOP 事务入口: {@link TransactionInterceptor#invoke(MethodInvocation)}
     * 组件以及流程介绍:
     * {@link TransactionAttributeSource}: 接口定义了对事务属性的获取功能
     * {@link TransactionAttribute}: 事务属性,由{@link AnnotationTransactionAttributeSource#findTransactionAttribute(Method)} 解析@Transaction 注解,将事务属性封装成 TransactionAttribute 对象
     * {@link PlatformTransactionManager} 事务管理器:将事务对象运用起来实现完整的事务功能则正是利用事务管理器,定义了 3 个事务方法
     * {@link AbstractPlatformTransactionManager} 主要实现了PlatformTransactionManager 定义的 3 个事务方法:getTransaction(),commit(),rollback()
     * {@link org.springframework.jdbc.datasource.DataSourceTransactionManager}: 数据源事务管理器,包含数据源事务相关操作的方法:
     * {@link org.springframework.jdbc.datasource.DataSourceTransactionManager#obtainDataSource()}: 获取数据源信息
     * {@link org.springframework.jdbc.datasource.DataSourceTransactionManager#doGetTransaction()}: 获取数据源事务对象 DataSourceTransactionObject
     * {@link org.springframework.jdbc.datasource.DataSourceTransactionManager.DataSourceTransactionObject} 事务对象,包括数据源连接信息、隔离级别、只读模式、超时时间、绑定当前线程
     * 每开启一个事务都会创建一个事务对象，事务对象提供的功能就是 获取连接、创建回滚点、将事务回滚到特定的回滚点, 释放回滚点等等操作。
     * 尤其要注意的是不新起事务就不会重新获取数据源,在同一个线程里要切换数据源就要新起事务,可以利用事务传播特性 REQUIRES_NEW(挂起外部事务,创建新事物)来解决
     * {@link org.springframework.jdbc.datasource.DataSourceTransactionManager#doBegin(Object, TransactionDefinition)}: 开启事务
     * {@link org.springframework.jdbc.datasource.DataSourceTransactionManager#doSuspend(Object)}: 挂起事务
     * {@link org.springframework.jdbc.datasource.DataSourceTransactionManager#doCommit(DefaultTransactionStatus)}: 提交事务
     * {@link org.springframework.jdbc.datasource.DataSourceTransactionManager#doRollback(DefaultTransactionStatus)}: 回滚事务
     * {@link org.springframework.jdbc.datasource.DataSourceTransactionManager#doCleanupAfterCompletion(Object)}: 事务完成后清除当前事务
     * {@link TransactionStatus}:事务状态, DefaultTransactionStatus是其默认实现, 里面维护了一个Object类型的transaction属性, 这个属性在通常情况下则为DataSourceTransactionObject事务对象
     * 根据 TransactionAttributeSource 获取 TransactionAttribute,
     * 根据TransactionAttribute 获取 PlatformTransactionManager,
     * PlatformTransactionManager的实现类 AbstractPlatformTransactionManager 实现getTransaction(),commit(),rollback(),定义了一套获取事务,提交事务,回滚事务的方法
     * DataSourceTransactionManager 实现 AbstractPlatformTransactionManager 定义的抽象方法,实现具体获取事务,提交事务,挂起事务,回滚事务,清理事务的方法
     */
    public static void invoke() {
        /*
		主线 2: Spring 事务调用流程
		Spring AOP 事务入口:
		TransactionInterceptor#invoke()
    	-> TransactionAspectSupport#invokeWithinTransaction()
        // 1.创建事务
        ->createTransactionIfNecessary()
            ->AbstractPlatformTransactionManager#getTransaction()
                ->doGetTransaction() 根据数据源获取事务对象DataSourceTransactionObject
                ->handleExistingTransaction() 如果事务已存在,根据不同传播机制不同处理
                ->doBegin()如果事务不存在，根据不同的传播机制处理，创建新事务
                    ->往DataSourceTransactionObject 设置属性：连接信息、隔离级别、只读模式、超时时间、绑定当前线程
            <== getTransaction() 返回TransactionStatus 对象，包含事务对象
        <==createTransactionIfNecessary() 封装TransactionStatus对象，返回TransactionInfo
        // 2.通过回调执行目标方法
        -> InvocationCallback#proceedWithInvocation()
        // 3.目标方法执行抛出异常，根据异常类型执行事务提交或者回滚操作
        -> completeTransactionAfterThrowing()
            ->AbstractPlatformTransactionManager#rollback()
            ->AbstractPlatformTransactionManager#commit()
        // 4.清理当前线程事务信息
        -> cleanupTransactionInfo()
        // 5.目标方法执行成功，提交事务
        -> commitTransactionAfterReturning()
            ->AbstractPlatformTransactionManager#commit()
		 */
    }
}
