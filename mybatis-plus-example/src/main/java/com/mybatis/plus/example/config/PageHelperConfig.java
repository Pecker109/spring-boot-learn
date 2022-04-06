package com.mybatis.plus.example.config;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author Pecker
 * @Description 解决 mybatis-plus 导致 PageHelper 失效问题
 * @since 2021-12-22
 */
@Configuration
public class PageHelperConfig {
    @Bean
    public PageHelper pageHelper() {

        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum", "true");
        p.setProperty("rowBoundsWithCount", "true");
        p.setProperty("reasonable", "true");
        pageHelper.setProperties(p);
        return pageHelper;
    }
}
