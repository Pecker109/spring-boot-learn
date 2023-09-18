package com.example.demo;

import cn.hutool.core.util.StrUtil;
import com.example.demo.model.Student;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Function;

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
        Student student =new Student();
        student.setAge(15);
        student.setName("令狐冲");
        String apply = buildKey().apply(student);
        System.out.println(apply);
    }

    public static Function<Student, String> buildKey() {
        return (Student obj) -> StringUtils.joinWith(StrUtil.AT, obj.getAge(), obj.getName());
    }

}
