package com.example.demo.singleton;

/**
 * @author Pecker
 * @Description 同步的懒汉单例, 同步的是 getInstance() 方法块
 * <p>
 * 1.线程安全
 * 2.加synchronized 同步,效率低,也不推荐使用
 * </p>
 * @since 2020-08-09
 */
public class SynLazySingleton2 {

    private static SynLazySingleton2 instance;

    public SynLazySingleton2() {
    }

    public static synchronized SynLazySingleton2 getInstance() {
        if (null == instance) {
            instance = new SynLazySingleton2();
        }
        return instance;
    }
}
