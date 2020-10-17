package com.example.demo.handler;

/**
 * @author Pecker
 * @Description 订单处理器, 抽象类, 具体的订单处理逻辑根据订单类型实现具体的实现类
 * @since 2020-08-23
 */
public abstract class AbstractHandler {
    abstract public int handle();
}

