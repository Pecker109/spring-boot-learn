package com.mybatis.plus.example.controller;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceStatValue;
import com.mybatis.plus.example.model.Security;
import com.mybatis.plus.example.service.impl.JdbcTemplateUtilService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Pecker
 * @Description JDBCTemplate 测试
 * @since 2021-12-22
 */
@Slf4j
@RestController
@RequestMapping("/jdbc")
public class JDBCTemplateController {


    @Autowired
    JdbcTemplateUtilService jdbcTemplateUtilService;

    @GetMapping("/stream")
    public void getConfig() {
        // 流式查询
        String sql = "select exchange_type ,security_code,security_name from t_securityinfo limit 1,1000";
        jdbcTemplateUtilService.streamAndProcess(sql, Security.class, 2000, System.out::println);
    }


    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/connect")
    public void connect() {
        DruidDataSource dataSource = (DruidDataSource) jdbcTemplate.getDataSource();

        assert dataSource != null;
        log.info("活跃连接数 : {}", dataSource.getActiveCount());
        log.info("已回收泄漏连接 : {}", dataSource.getRemoveAbandonedCount());
        log.info("已关闭连接 : {}", dataSource.getCloseCount());
    }


}
