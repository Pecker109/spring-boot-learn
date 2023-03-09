package com.example.demo.singleton;

/**
 * @author Pecker
 * @Description 懒汉式单例
 * <p>
 * 1.使用该类的时候才进行实例化
 * 2.线程不安全
 * </p>
 * @since 2020-08-09
 */
public class LazySingleton1 {

    private static LazySingleton1 instance;

    private LazySingleton1() {
    }

    public static LazySingleton1 getInstance() {
        if (null == instance) {
            instance = new LazySingleton1();
        }
        return instance;
    }
}
