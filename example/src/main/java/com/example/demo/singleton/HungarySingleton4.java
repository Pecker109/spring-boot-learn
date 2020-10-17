package com.example.demo.singleton;

/**
 * @author Pecker
 * @Description 饿汉模式
 * <p>
 *     无线程安全,但是不能延迟加载,影响系统性能
 * </p>
 * @since 2020-08-09
 */
public class HungarySingleton4 {
    private static HungarySingleton4 instance = new HungarySingleton4();
    private HungarySingleton4(){}

    public static HungarySingleton4 getInstance(){
        return instance;
    }
}
