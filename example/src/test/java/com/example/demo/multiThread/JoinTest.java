package com.example.demo.multiThread;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2021-04-13
 */
@Slf4j
public class JoinTest {

    public static void main(String[] args) {
        // 初始化前置线程为主线程
        Thread preThread = Thread.currentThread();
        for (int i = 0; i < 10; i++) {
            JoinDemo joinDemo = new JoinDemo(preThread, i);
            joinDemo.start();
            preThread = joinDemo;
        }

        System.out.println("主线程执行结束...");
    }

    public static class JoinDemo extends Thread {
        int i;
        Thread preThread; //上一个线程

        public JoinDemo(Thread previousThread, int i) {
            this.preThread = previousThread;
            this.i = i;
        }

        @Override
        public void run() {
            try {
                //调用上一个线程的join方法，演示的时候可以把这行代码注释掉
                /* join(long millis) 方法为: 当前线程会被挂起,去先执行preThread 线程的 run()方法,
                 * 参数millis 表示会等待preThread 多久, 0 表示会一直等待preThread 线层执行结束
                 *
                 * 实现原理:
                 * 1.当前线程是如何被挂起的:
                 *    线程是join方法的本质调用的是Object中的wait方法实现线程的阻塞,
                 *      有很多人不理解join为什么阻塞的是主线程呢?
                 *      不理解的原因是阻塞主线程的方法是放在previousThread这个实例作用，
                 *      让大家误以为应该阻塞previousThread线程。
                 *      实际上主线程会持有previousThread这个对象的锁，
                 *      然后调用wait方法去阻塞，而这个方法的调用者是在主线程中的。所以造成主线程阻塞。
                 * 1.在什么时候唤醒主线程的？源码中并没有显示的调用 notify() 方法呀
                 *   Thread.join其实底层是通过wait/notifyall来实现线程的通信达到线程阻塞的目的；
                 *   当线程执行结束以后，会触发两个事情:
                 *   第一个是设置native线程对象为null、
                 *   第二个是通过notifyall方法，让等待在previousThread对象锁上的wait方法被唤醒。
                 */
                log.info("当前线程: {} , i = {}", currentThread(), i);
                preThread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("num:" + i);
        }
    }


    // 这个方法可以证明 threadA 会被先执行,主线程会被阻塞,可以把join理解成一个普通方法！真正阻塞的不是调用者线程，而是当前正在执行的线程。
    //public static void main(String[] args) throws InterruptedException {
    //    Thread threadA = new Thread(()->{
    //        try {
    //            TimeUnit.SECONDS.sleep(2);
    //        } catch (InterruptedException e) {
    //            e.printStackTrace();
    //        }
    //        System.out.println(Thread.currentThread().getName()+":想先执行");
    //    },"线程A");
    //    //开启一个线程A
    //    threadA.start();
    //    //主线程会持有子线程的锁,子线程还没开始主线程就阻塞了,等待子线程结束后通知;
    //    threadA.join();
    //    System.out.println(Thread.currentThread().getName()+ "线程执行");
    //}

}
