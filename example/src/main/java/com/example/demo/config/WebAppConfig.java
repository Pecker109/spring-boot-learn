package com.example.demo.config;

import com.example.demo.interceptor.ResponseResultInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2020-10-17
 */
@Slf4j
@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Bean
    public ResponseResultInterceptor responseResultInterceptor() {
        return new ResponseResultInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.warn("加载拦截器配置=============");
        registry.addInterceptor(responseResultInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/exclude/**");
    }
}
