package com.example.demo.spring;

import com.example.demo.model.Student;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;

import javax.sql.DataSource;
import java.lang.reflect.Proxy;

/**
 * InstantiationAwareBeanPostProcessor 是 Spring 框架中的一个扩展接口，继承自 BeanPostProcessor。
 * 它允许开发者在 Bean 实例化阶段（即对象创建和依赖注入阶段）插入自定义逻辑，从而实现对 Bean 创建过程的细粒度控制。
 */
public class InstantiationAwareBeanPostProcessorTest implements InstantiationAwareBeanPostProcessor {

   /*
    1.调用 doCreateBean()之前
        postProcessBeforeInstantiation(): 若返回非 null 对象，直接作为 Bean 使用，跳过后续步骤
    2. Bean 实例化-doCreateBean-1
    3. 属性填充 doCreateBean-3
       3.1: postProcessAfterInstantiation(): 返回 false 可跳过属性注入
       3.2: 属性注入（依赖注入）: postProcessProperties()
    4. 生命周期回调: BeanPostProcessor 的其他回调（如 @PostConstruct、InitializingBean）
    */

    /**
     * 调用时机：在 Bean 实例化前（doCreateBean 之前）。
     * 在调用 doCreateBean()之前会先判断是否为InstantiationAwareBeanPostProcessor 的实例,再执行它的postProcessBeforeInstantiation()方法.
     * 注意这里的postProcessBeforeInstantiation()方法和{@link BeanPostProcessor#postProcessBeforeInitialization(Object, String)} 不是一个方法
     * 功能：
     * - 可以返回一个自定义对象，完全替代 Spring 默认的实例化流程。
     * - 若返回非 null 对象，Spring 将跳过后续的实例化和属性注入，直接使用该对象作为 Bean。
     * 典型应用：
     * - 提前生成代理对象（如 AOP 动态代理）。
     * - 阻止某些 Bean 的实例化（如根据条件返回 null）。
     *
     * @param beanClass the class of the bean to be instantiated
     * @param beanName  the name of the bean
     * @return return
     * @throws BeansException e
     */
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        // 示例
        if (beanClass == Student.class) {
            // 返回代理对象，替代原始 Bean 实例
            return Proxy.newProxyInstance(
                    beanClass.getClassLoader(),
                    new Class[]{beanClass},
                    (proxy, method, args) -> {
                        System.out.println("Before method: " + method.getName());
                        return method.invoke(proxy, args);
                    }
            );
        }
        return null; // 默认流程继续
    }

    /**
     * 调用时机：在 Bean 实例化后（doCreateBean-1 之后），但属性填充（doCreateBean-3）前。
     * 功能：
     * - 返回 boolean 值，决定是否继续执行属性注入：
     * -- true：继续属性注入（默认）。
     * -- false：跳过属性注入（需手动处理依赖）。
     * 典型应用：
     * - 在属性注入前执行某些初始化逻辑。
     * - 根据条件跳过某些 Bean 的属性注入。
     *
     * @param bean     the bean instance created, with properties not having been set yet
     * @param beanName the name of the bean
     * @return
     * @throws BeansException
     */
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if (bean instanceof Student) {
            // 手动初始化某些属性
            return false; // 阻止 Spring 自动注入属性
        }
        return true; // 允许属性注入
    }

    /**
     * 调用时机：在属性填充(doCreateBean-3)前。
     * 功能：
     * - 修改或过滤即将注入的属性值（PropertyValues）。
     * - 可添加、删除或替换属性值。
     * 典型应用：
     * - 动态加密/解密敏感字段（如数据库密码）。
     * - 根据环境变量覆盖配置属性。
     * Spring 内置应用:
     * - {@link AutowiredAnnotationBeanPostProcessor#postProcessProperties(PropertyValues, Object, String)}:处理@Autowired注解,进行属性注入
     * - {@link CommonAnnotationBeanPostProcessor#postProcessProperties(PropertyValues, Object, String)}:处理@Resource注解,进行属性注入
     *
     * @param pvs      the property values that the factory is about to apply (never {@code null})
     * @param bean     the bean instance created, but whose properties have not yet been set
     * @param beanName the name of the bean
     * @return
     * @throws BeansException
     */
    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName)
            throws BeansException {
        if (bean instanceof DataSource) {
            MutablePropertyValues mpvs = (MutablePropertyValues) pvs;
            // 对密码字段解密
            String encryptedPassword = mpvs.getPropertyValue("password").getValue().toString();
            String decryptedPassword = "decrypt(encryptedPassword)";
            mpvs.add("password", decryptedPassword);
            return mpvs;
        }
        return pvs;
    }
}
