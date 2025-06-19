package com.example.demo.multiThread;

/**
 * @author Pecker
 * @Description ThreadLocal 测试
 * @since 2021-05-27
 */
public class ThreadLocalTest {
    /**
     * ThreadLocal变量，每个线程都有一个副本，互不干扰
     * 原理: 看 ThreadLocal#get()方法
     * 1.拿到当前线程,从当前线程中获取 ThreadLocalMap
     * 2.注意 ThreadLocalMap 是 Thread 的属性,这也是为什么是和线程相关的原因
     * 3.ThreadLocalMap 内部实现是通过维护一个 Entry 数组,数组中的每个元素是一个 Entry 对象,每个 Entry 对象中包含一个 key-value 对,其中 key 就是 ThreadLocal 对象, value 就是需要存储的值
     * 4.为什么要是一个 map?因为一个线程内可以有多个ThreadLocal 变量
     * Entry Entry继承自WeakReference弱引用,因此需要注意一下问题:
     * 1.弱引用不会自动触发对象 GC，因此需要在适当的时机手动触发 GC。
     * 2.由于弱引用在对象被回收时才被释放，因此在使用弱引用时，可能会遇到对象已经回收的情况，导致 NullPointerException,因为如果一个ThreadLocal不存在外部强引用时,Key(ThreadLocal)势必会被GC回收，这样就会导致ThreadLocalMap中key为null， 而value还存在着强引用，只有thead线程退出以后,value的强引用链条才会断掉。
     * 3.ThreadLocal在没有外部对象强引用时，发生GC时弱引用Key会被回收，而Value不会回收。当线程没有结束，但是ThreadLocal已经被回收，则可能导致线程中存在ThreadLocalMap<null, Object>的键值对，造成内存泄露。（ThreadLocal被回收，ThreadLocal关联的线程共享变量还存在）。
     * 为避免内存泄露，需要在线程结束时，调用ThreadLocal的remove方法，手动删除ThreadLocalMap中的键值对。或者将ThreadLocal定义为private static静态变量，这样ThreadLocal的生命周期与线程相同，在线程结束时，ThreadLocalMap也会被回收。
     */

    public static void main(String[] args) throws Exception {
        threadLocalTest();
        System.gc();
        System.out.println();
    }

    public static void threadLocalTest() throws Exception {
        // 主线程设置值
     ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();
        THREAD_LOCAL.set("111111");
        String v = THREAD_LOCAL.get();
        System.out.println("Thread-0线程执行之前，" + Thread.currentThread().getName() + "线程取到的值：" + v);

        //new Thread(() -> {
        //    String v1 = THREAD_LOCAL.get();
        //    System.out.println(Thread.currentThread().getName() + "线程取到的值：" + v1);
        //    // 设置 threadLocal
        //    THREAD_LOCAL.set("222222");
        //    v1 = THREAD_LOCAL.get();
        //    System.out.println("重新设置之后，" + Thread.currentThread().getName() + "线程取到的值为：" + v1);
        //    System.out.println(Thread.currentThread().getName() + "线程执行结束");
        //}).start();

        // 等待所有线程执行结束
        //Thread.sleep(3000L);
        System.gc();
        //v = THREAD_LOCAL.get();
        System.out.println("Thread-0线程执行之后，" + Thread.currentThread().getName() + "线程取到的值：" + v);
    }
}
