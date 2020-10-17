package com.example.demo.singleton;

/**
 * @author Pecker
 * @Description 静态内部类模式
 * <p>
 * 静态内部类，线程安全，主动调用时才实例化，延迟加载效率高，推荐使用。
 * </p>
 * @since 2020-08-09
 */
public class StaticInternalSingleton5 {

    private static class SingletonHolder {
        private static final StaticInternalSingleton5 INSTANCE = new StaticInternalSingleton5();
    }

    private StaticInternalSingleton5() {
    }

    public static final StaticInternalSingleton5 getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
