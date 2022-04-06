package com.example.demo.multiThread;

import lombok.SneakyThrows;

/**
 * @author Pecker
 * @Description 线程 A sleep(),线程 B 中断线程 A
 * @since 2020-04-23
 */
public class InterruptExample {

    /**
     * 关于线程中断必须要知道的点:
     * 1.调用线程的 interrupt() 方法只是给目标线程发一个中断信号，线程被打上中断标记。而线程是否被中断或者说是否会被杀死取决于被中断线程的处理
     * 2.处理线程的中断有两种形式: 针对于程序中调用了 sleep(),wait() 等方法的中断处理是会抛出异常 InterruptedException,但是要注意的是抛出InterruptedException 异常之后 中断状态会被清除掉
     * 另外一种中断处理方式是通过程序中手动判断 isInterrupted() 去处理
     */

    @SneakyThrows
    public static void main(String[] args) {
        final Thread t1 = new Thread() {
            @Override
            public void run() {
                System.out.println("Thread1 start!");
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.println("Thread1 sleep start!");
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        System.out.println("Thread1 sleep exception e=" + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread t2 = new Thread() {
            @Override
            public void run() {
                System.out.println("Thread2 start!");
                while (true) {
                    System.out.println("Thread2 sleep start!");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        System.out.println("Thread2 sleep exception e=" + e);
                        e.printStackTrace();
                    }
                    t1.interrupt();
                }
            }
        };
        t1.start();
        t2.start();
    }
}
