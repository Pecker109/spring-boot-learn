package com.pecker.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2020-04-05
 */
@Data
//@Component
//@ConfigurationProperties(prefix = "student")
public class ApolloConfig {
    public String name;
}
