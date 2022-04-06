package com.mybatis.plus.example.controller;

import com.mybatis.plus.example.service.PropagationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Pecker
 * @Description 事务传播特性测试
 * @since 2021-12-17
 */
@RestController
@RequestMapping("/tx/p/")
public class PropagationController {
    @Autowired
    PropagationService propagationService;

    @GetMapping("/require")
    public String select1() {
        propagationService.propagationTest();
        return "success";
    }
}
