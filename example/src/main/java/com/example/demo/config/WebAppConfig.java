package com.example.demo.config;

import com.example.demo.interceptor.ResponseResultInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2020-10-17
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Bean
    public ResponseResultInterceptor responseResultInterceptor() {
        return new ResponseResultInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(responseResultInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/exclude/**");
    }
}
