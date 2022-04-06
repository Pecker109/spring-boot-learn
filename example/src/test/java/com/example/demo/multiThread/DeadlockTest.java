package com.example.demo.multiThread;


/**
 * @author Pecker
 * @Description 死锁
 * @since 2022-02-10
 */
public class DeadlockTest {

    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void main(String[] args) throws Exception {
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                synchronized (lock1) {
                    System.out.println("thread1 synchronized lock1");
                    synchronized (lock2) {
                        System.out.println("thread1 synchronized lock2");
                    }
                }
            }
        }, "thread1").start();

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                synchronized (lock2) {
                    System.out.println("thread2 synchronized lock2");
                    synchronized (lock1) {
                        System.out.println("thread2 synchronized lock1");
                    }
                }
            }
        }, "thread2").start();
    }
}
