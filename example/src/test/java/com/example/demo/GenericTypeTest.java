package com.example.demo;


import com.example.demo.handler.ResponseResultHandler;
import org.springframework.core.ResolvableType;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

public class GenericTypeTest {

    /**
     * 使用 Spring 的 ResolvableType 解析泛型类型
     *
     * @param instance       实现类的实例
     * @param interfaceIndex 接口在继承链中的索引（0为第一个）
     * @return 泛型参数 T 的 Class 对象
     */
    public static Class<?> resolveGenericType(Object instance, int interfaceIndex, Class<?> type) {
        ResolvableType resolvableType = ResolvableType.forInstance(instance).as(type);
        ResolvableType[] generics = resolvableType.getGenerics();
        if (generics.length > 0) {
            return generics[interfaceIndex].resolve();
        }
        throw new IllegalArgumentException("无法解析泛型类型");
    }

    public static void main(String[] args) {
        /*
         可以通过 Spring 上下文的方式获取实例对象
        ApplicationContext.getBean("beanName");
         */
        ResponseResultHandler obj = new ResponseResultHandler();
        Class<?> tClass = resolveGenericType(obj, 0, ResponseBodyAdvice.class);
        System.out.println(tClass);
    }
}
