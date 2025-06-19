package com.example.demo.spring;


import org.springframework.beans.factory.InitializingBean;

public class InitializingBeanTest implements InitializingBean {
    /**
     * 通常作用是:
     * 1.在 Bean 的依赖注入完成后，执行自定义初始化逻辑（如数据校验、资源加载等）。
     * 2.依赖可用性保证,确保所有属性已正确注入，避免因依赖未就绪导致的运行时异常。
     * 执行时机:doCreateBean-4中
     * -- BeanPostProcessor.postProcessBeforeInitialization()
     * ↓
     * -- 初始化方法执行（`InitializingBean.afterPropertiesSet()` 或 `init-method`）
     * ↓
     * -- BeanPostProcessor.postProcessAfterInitialization()
     *
     * @throws Exception e
     */
    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
