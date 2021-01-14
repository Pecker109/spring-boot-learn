package com.example.demo.chain;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2021-01-14
 */
@Order(2)
@Component
public class SecondChain extends AbstractChain {
    @Override
    public void process() {
        System.out.println("调用链 2");
    }
}
