package com.example.demo;

import com.example.demo.chain.Handler;
import com.example.demo.util.ClassUtil;

import java.util.List;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2021-01-14
 */
public class ClassUtilTest {
    public static void main(String[] args) throws Exception {
        List<Class<?>> classList =  ClassUtil.getAllAssignedClass(Handler.class);
        classList.forEach( cls -> System.out.println(cls.getName()));
    }
}
