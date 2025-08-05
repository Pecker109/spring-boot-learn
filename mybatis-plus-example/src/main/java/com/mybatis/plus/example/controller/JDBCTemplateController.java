package com.mybatis.plus.example.controller;

import com.mybatis.plus.example.model.Security;
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
    JdbcTemplateUtilService jdbcTemplateUtilService;

    @GetMapping("/stream")
    public void getConfig() {
        // 流式查询
        String sql = "select exchange_type ,security_code,security_name from t_securityinfo";
        jdbcTemplateUtilService.streamAndProcess(sql, Security.class, 2000, System.out::println);
    }


}
