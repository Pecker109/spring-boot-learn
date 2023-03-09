package com.mybatis.plus.example.controller;

import com.github.pagehelper.PageInfo;
import com.mybatis.plus.example.model.NodeDO;
import com.mybatis.plus.example.service.JDBCTemplateService;
import com.mybatis.plus.example.service.impl.JdbcTemplateUtilService;
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

    @Autowired
    JdbcTemplateUtilService jdbcTemplateUtilService;

    @GetMapping("/getConfig")
    public void getConfig() {
        jdbcTemplateService.getConfig();
    }

    @GetMapping("/select")
    public void select() {
        jdbcTemplateService.select();
    }


    @GetMapping("/jdbc")
    public String jdbc() {
        String sql = "SELECT * FROM node";
        PageInfo pageInfo = jdbcTemplateUtilService.queryByPageForMySQL(null, null, 1, 10, NodeDO.class);
        return "sql";
    }
}
