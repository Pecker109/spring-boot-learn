package com.example.demo.controller;

import com.example.demo.chain.AbstractChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Pecker
 * @Description 责任链调用
 * @since 2021-01-15
 */
@RestController
public class ChainController {
    /**
     * 用了这么久的 @Autowired ,都是通过字段方式注入的,作为成员变量注入,
     * 原来 Spring 更推荐构造方法进行依赖注入
     */
//    @Autowired
//    private List<AbstractChain> abstractChainList;

    private final List<AbstractChain> abstractChainList;

    @Autowired
    public ChainController(List<AbstractChain> abstractChainList) {
        this.abstractChainList = abstractChainList;
    }

    @GetMapping("/index4")
    public void index4() {
        abstractChainList.forEach(chain ->{
            chain.process();
            System.out.println(chain.getNext());
        });
    }
}
