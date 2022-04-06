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
     * 3.ThreadLocalMap 就是一个 map,key 是 ThreadLocal,value 是对应的值
     * 4.为什么要是一个 map?因为一个线程内可以有多个ThreadLocal 变量
     */
    public static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

    public static void main(String[] args) throws Exception {
        threadLocalTest();
    }

    public static void threadLocalTest() throws Exception {
        // 主线程设置值
        THREAD_LOCAL.set("111111");
        String v = THREAD_LOCAL.get();
        System.out.println("Thread-0线程执行之前，" + Thread.currentThread().getName() + "线程取到的值：" + v);

        new Thread(() -> {
            String v1 = THREAD_LOCAL.get();
            System.out.println(Thread.currentThread().getName() + "线程取到的值：" + v1);
            // 设置 threadLocal
            THREAD_LOCAL.set("222222");
            v1 = THREAD_LOCAL.get();
            System.out.println("重新设置之后，" + Thread.currentThread().getName() + "线程取到的值为：" + v1);
            System.out.println(Thread.currentThread().getName() + "线程执行结束");
        }).start();

        // 等待所有线程执行结束
        Thread.sleep(3000L);
        v = THREAD_LOCAL.get();
        System.out.println("Thread-0线程执行之后，" + Thread.currentThread().getName() + "线程取到的值：" + v);
    }
}
