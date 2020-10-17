package com.example.demo.annotation;

import java.lang.annotation.*;

/**
 * @author Pecker
 * @Description 策略 key 标识, 根据这个注解来指定 key,获取具体要执行的策略类
 * @since 2020-08-23
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface StrategyKeyFlag {
    int value() default 0;
}
