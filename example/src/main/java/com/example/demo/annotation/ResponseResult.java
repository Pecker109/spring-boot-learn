package com.example.demo.annotation;

import java.lang.annotation.*;

/**
 * @author Pecker
 * @Description 封装返回结果, 主要用于 Controller 类上
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseResult {
}
