package com.example.demo;

import java.util.regex.Pattern;

/**
 * @author Pecker
 * @Description 正则测试
 * @since 2023-03-22
 */
public class RegexTest {

    public static void main(String[] args) {
        String code = "5104";

        String pattern = "510.*4.*";

        boolean isMatch = Pattern.matches(pattern, code);
        System.out.println(isMatch);
    }
}
