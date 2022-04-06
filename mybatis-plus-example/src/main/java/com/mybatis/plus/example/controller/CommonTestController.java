package com.mybatis.plus.example.controller;

import com.mybatis.plus.example.service.CommonTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Pecker
 * @Description 测试
 * @since 2021-12-22
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class CommonTestController {
    @Autowired
    CommonTestService commonTestService;

    @GetMapping("/test1")
    public String test1(String flag) {
        log.info("当前线程: {}", Thread.currentThread().getName());
        return commonTestService.test1(flag);
    }

}
