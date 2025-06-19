package com.example.demo.spring;


import org.springframework.beans.factory.FactoryBean;

public class FactoryBeanTest implements FactoryBean {


    /*
    Spring FactoryBean 详解:
    FactoryBean 是 Spring 框架中一个特殊的接口，用于封装复杂对象的创建逻辑。
        它允许开发者通过自定义的工厂方法生成 Bean 实例，适用于需要动态构建、配置复杂或依赖外部资源的场景。
        与普通 Bean 不同，FactoryBean 本身是一个工厂，实际注入到容器中的是它生产的对象，而非工厂本身。
    一、FactoryBean 的核心作用
        - 封装复杂实例化逻辑：当 Bean 的创建过程涉及多步操作（如连接池初始化、第三方库配置）时，FactoryBean 可以集中管理这些逻辑。
        - 延迟加载与动态生成：支持按需生成对象，避免资源浪费。
        - 与 Spring 容器解耦：将对象的创建过程与 Spring 的生命周期分离，提升灵活性。
    二、FactoryBean 与普通 Bean 的区别
        维度:	普通 Bean	<-->	FactoryBean
        容器中的实例:	Bean 自身实例直接注入容器。 <-->	注入的是 getObject() 返回的对象，而非 FactoryBean 本身。
        获取方式:	通过 applicationContext.getBean("beanName") 获取。	<-->	获取 FactoryBean 自身需在 Bean 名前加 &（如 &myFactoryBean）。
        适用场景:	简单对象的实例化（如 POJO）。	<-->	复杂对象创建（如 MyBatis 的 SqlSessionFactoryBean）。
    三、与 BeanFactory 的区别
        FactoryBean	<-->	BeanFactory
        用于生成特定 Bean 的工厂。	<-->	Spring 容器的顶层接口，负责管理所有 Bean。
        是 Spring 容器中的一个 Bean。	<-->	是容器的核心基础设施。
    四、典型使用场景
        - 集成第三方框架：如 MyBatis 的 SqlSessionFactoryBean，用于构建 SqlSessionFactory。
        - 动态代理生成：通过 FactoryBean 封装 AOP 代理对象的创建逻辑。
        - 资源密集型对象：如数据库连接池、线程池的初始化。
    五、Spring 内置的 FactoryBean 实现
        实现类	<-->	作用
        SqlSessionFactoryBean	<-->	创建 MyBatis 的 SqlSessionFactory。
        ProxyFactoryBean	<-->	生成 AOP 动态代理对象。
        JndiObjectFactoryBean	<-->	从 JNDI 查找资源（如数据源）。
     */

    /**
     * 返回工厂创建的对象实例
     * 单例（isSingleton() = true）：每次 getBean() 返回同一对象（默认）。
     * 原型（isSingleton() = false）：每次 getBean() 生成新对象。
     *
     * @return 返回工厂创建的对象实例
     * @throws Exception e
     */
    @Override
    public Object getObject() throws Exception {
        return null;
    }

    /**
     * 返回对象类型（用于类型检查）
     *
     * @return return
     */
    @Override
    public Class<?> getObjectType() {
        return null;
    }

    /**
     * 是否为单例（默认 true）
     *
     * @return return
     */
    @Override
    public boolean isSingleton() {
        return FactoryBean.super.isSingleton();
    }
}
