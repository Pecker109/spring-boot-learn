package com.example.demo.chain;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 也可以验证 @Order 的作用: 并不能决定类的加载顺序,而是在 list 中的顺序 List<AbstractChain> abstractChainList
 * {@link javax.annotation.Priority} 和 @Order 作用是一样的,都是只对同一类型的实例有效
 *
 * @author Pecker
 * @Description 类信息
 * @since 2021-01-14
 */
@Order(2)
@Component
public class OneChain extends AbstractChain {
    @Override
    public void process() {
        System.out.println("调用链 1");
    }
}
