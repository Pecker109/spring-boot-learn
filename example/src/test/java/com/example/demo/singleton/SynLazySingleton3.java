package com.example.demo.singleton;

/**
 * @author Pecker
 * @Description 同步的懒汉模式象
 * <p>
 * 1.相较于SynLazySingleton2 同步的是 new 对象的时候
 * 分析:
 * 对象的创建过程:
 * 1.分配内存空间
 * 2.初始化对象
 * 3.将内存空间的地址赋值给对象的引用
 * 但是,jvm 可能会对代码进行指令重拍,上述过程可能会变成 1->3->2 执行,
 * 所以当第二个线程进行判空操作时,虽然不为空,但是第一个线程的对象初始化过程还没有完全结束,其实还是个空对象,
 * 第二个线程拿到的就是个空对象了,即使是双重判空也会存在这个问题
 * 解决指令重排序可以加上volatile 关键字
 * </p>
 * @since 2020-08-09
 */
public class SynLazySingleton3 {

    //volatile 防止jvm 重排序带来的问题
    private volatile static SynLazySingleton3 instance;

    private SynLazySingleton3() {
    }

    public static SynLazySingleton3 getInstance() {
        if (null == instance) {
            synchronized (SynLazySingleton3.class) {
                instance = new SynLazySingleton3();
            }
        }
        return instance;
    }

    /**
     * 双重校验锁
     *
     * @return instance
     */
    public static SynLazySingleton3 getInstance1() {
        if (null == instance) {
            synchronized (SynLazySingleton3.class) {
                if (null == instance) {
                    instance = new SynLazySingleton3();
                }
            }
        }
        return instance;
    }
}
