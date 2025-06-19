package com.mybatis.plus.example.config;

import com.mybatis.plus.example.injector.TestInsertBatchSqlInjector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {


    /**
     * 自定义批量插入 SQL 注入器
     */
    @Bean
    public TestInsertBatchSqlInjector insertBatchSqlInjector() {
        return new TestInsertBatchSqlInjector();
    }

}