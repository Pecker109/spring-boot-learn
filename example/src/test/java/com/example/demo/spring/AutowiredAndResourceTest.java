package com.example.demo.spring;

public class AutowiredAndResourceTest {
    /*
@Resource 与 @Autowired 依赖注入的区别:
     一、注解特性对比
        特性:	@Autowired  <-->	@Resource
        标准:	Spring 原生注解	<-->	JSR-250 标准（Java 规范）
        注入方式:	默认按类型（byType）	<-->	默认按名称（byName，失败后回退到 byType）
        是否支持 required:	是（@Autowired(required=false)）	<-->	否（找不到 Bean 时直接抛异常）
        多候选 Bean 处理:	需配合 @Qualifier 指定名称	<-->	通过 name 属性指定 Bean 名称
        支持注入目标:	字段、构造方法、Setter 方法、普通方法	<-->	字段、Setter 方法
     二、依赖注入的源码流程
        1. 整体流程
        依赖注入发生在 Bean 的 属性填充阶段（populateBean 方法），由 BeanPostProcessor 处理：
        - @Autowired：由 AutowiredAnnotationBeanPostProcessor 处理。
        - @Resource：由 CommonAnnotationBeanPostProcessor 处理。
        关键源码入口：
        // AbstractAutowireCapableBeanFactory#populateBean
        protected void populateBean(String beanName, RootBeanDefinition mbd, @Nullable BeanWrapper bw) {
            // 遍历所有 BeanPostProcessor 处理属性注入
            for (BeanPostProcessor bp : getBeanPostProcessors()) {
                if (bp instanceof InstantiationAwareBeanPostProcessor) {
                    InstantiationAwareBeanPostProcessor ibp = (InstantiationAwareBeanPostProcessor) bp;
                    // 处理 @Autowired、@Resource 等注解
                    PropertyValues pvsToUse = ibp.postProcessProperties(pvs, bw.getWrappedInstance(), beanName);
                }
            }
        }
        2. @Autowired 处理流程
        处理类：AutowiredAnnotationBeanPostProcessor
        步骤解析：
            2.1元数据收集：
            在 Bean 定义合并阶段（postProcessMergedBeanDefinition），解析 @Autowired 注解的字段和方法，生成 InjectionMetadata。
            public void postProcessMergedBeanDefinition(...) {
                // 查找 @Autowired 注解的元数据
                InjectionMetadata metadata = findAutowiringMetadata(beanName, beanType, pvs);
                metadata.checkConfigMembers(beanDefinition);
            }
            2.2属性注入：
            在 postProcessProperties 方法中，通过反射将依赖注入到目标 Bean。
            public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) {
                InjectionMetadata metadata = findAutowiringMetadata(beanName, bean.getClass(), pvs);
                try {
                    // 反射注入依赖
                    metadata.inject(bean, beanName, pvs);
                }
                return pvs;
            }
            2.3依赖解析逻辑：
            优先按类型匹配 Bean，若存在多个候选 Bean，则根据 @Qualifier 或 Bean 名称确定唯一候选。
            // DefaultListableBeanFactory#doResolveDependency
            public Object doResolveDependency(...) {
                // 按类型查找候选 Bean
                Map<String, Object> matchingBeans = findAutowireCandidates(beanName, type, descriptor);
                if (matchingBeans.size() > 1) {
                    // 根据 @Qualifier 或 Bean 名称确定唯一候选
                    String primaryCandidate = determinePrimaryCandidate(matchingBeans, type);
                }
            }
        3. @Resource 处理流程
        处理类：CommonAnnotationBeanPostProcessor
        步骤解析：
            3.1元数据收集：
            在 postProcessMergedBeanDefinition 阶段，解析 @Resource 注解的字段和方法，生成 InjectionMetadata。
            protected void findResourceMetadata(...) {
                // 解析 @Resource 注解
                ReflectionUtils.doWithLocalFields(targetClass, field -> {
                    Resource resource = field.getAnnotation(Resource.class);
                    if (resource != null) {
                        elements.add(new ResourceElement(field, resource));
                    }
                });
            }
            3.2属性注入：
            在 postProcessProperties 方法中，按名称或类型注入依赖。
            public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) {
                InjectionMetadata metadata = findResourceMetadata(beanName, bean.getClass(), pvs);
                metadata.inject(bean, beanName, pvs);
                return pvs;
            }
            3.3依赖解析逻辑：
            优先按 name 属性匹配 Bean，若未指定 name，则使用字段或方法名作为默认名称。若按名称未找到，则回退到按类型匹配。
            // CommonAnnotationBeanPostProcessor#autowireResource
            protected Object autowireResource(...) {
                if (name != null && !name.isEmpty()) {
                    // 按名称查找 Bean
                    bean = beanFactory.getBean(name, type);
                } else {
                    // 按类型查找 Bean
                    bean = beanFactory.resolveDependency(descriptor, beanName, autowiredBeanNames, typeConverter);
                }
            }
    三、关键区别总结
        维度:	@Autowired	<-->	@Resource
        处理类:	AutowiredAnnotationBeanPostProcessor	<-->	CommonAnnotationBeanPostProcessor
        注入顺序:	先按类型，再按名称（需 @Qualifier）	<-->	先按名称，再按类型
        多候选 Bean 处理:	需显式指定名称或 @Primary	<-->	默认根据字段/方法名匹配
        异常处理:	允许 required=false 避免注入失败	<-->	未找到 Bean 时直接抛出异常
        源码逻辑:	通过 InjectionMetadata 反射注入	<-->	通过 ResourceElement 处理名称与类型解析
    四、使用场景建议
        - @Autowired：
        适用于 Spring 生态内的依赖注入，尤其是需要按类型匹配或结合 @Qualifier 的场景。
        - @Resource：
        适用于需要按名称精确匹配依赖（如多数据源配置），或希望遵循 JSR-250 标准的项目。

通过理解两者的源码实现，可以更精准地选择适合的注入方式，并解决因依赖解析不当导致的运行时问题。
     */
}
