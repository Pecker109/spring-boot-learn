package com.example.demo.chain;

import lombok.Data;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2021-01-14
 */
@Data
public abstract class AbstractChain {
    private AbstractChain next;

    public abstract void process();
}
