package com.mybatis.plus.example.controller;

import com.mybatis.plus.example.service.JDBCTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Pecker
 * @Description JDBCTemplate 测试
 * @since 2021-12-22
 */
@RestController
@RequestMapping("/jdbc")
public class JDBCTemplateController {
    @Autowired
    JDBCTemplateService jdbcTemplateService;

    @GetMapping("/getConfig")
    public void getConfig() {
        jdbcTemplateService.getConfig();
    }

    @GetMapping("/select")
    public void select() {
        jdbcTemplateService.select();
    }
}
