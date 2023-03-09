package com.example.demo;

import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;

/**
 * idea 破解方式连接:https://www.exception.site/essay/how-to-free-use-intellij-idea-2019-3
 * 修改了配置文件: /Users/apple/Library/Application Support/JetBrains/IntelliJIdea2021.3/idea.vmoptions 的方式
 * 添加了配置:-javaagent:/Users/apple/Applications/conf/idea/ja-netfilter/ja-netfilter.jar
 *
 * @author Pecker
 * @Description 类信息
 * @since 2020-10-12
 */
public class CommonTest {


    public static void main(String[] args) throws Exception {
        String a ="\u003C \u003E";
        String b = "\u003c";
        System.out.println(new String(b.getBytes(), StandardCharsets.UTF_8));



    }

}
