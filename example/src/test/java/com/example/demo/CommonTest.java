package com.example.demo;

import org.springframework.util.Assert;

import java.util.Objects;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2020-10-12
 */
public class CommonTest {
    public static void main(String[] args) {

        String value = null;
        Objects.requireNonNull(value, "value 对象不为空");
        Assert.notNull(value, "value 对象不为空");

    }
}
