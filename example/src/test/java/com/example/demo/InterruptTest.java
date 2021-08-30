package com.example.demo;

/**
 * @author Pecker
 * @Description 线程中断测试
 * @since 2021-04-22
 */
public class InterruptTest {

    public static void main(String[] args) {
        //test1();
        //test2();
        //test3();
        //test4();
        test5();
    }

    /**
     * 示例中的线程不会被中断!
     * 因为虽然给线程发出了中断信号，但程序中并没有响应中断信号的逻辑，所以程序不会有任何反应。
     */
    private static void test1() {
        Thread thread = new Thread(() -> {
            while (true) {
                System.out.println("当前线程" + Thread.currentThread().getName());
                Thread.yield();
                System.out.println(Thread.currentThread().getName() + "执行结束");
            }
        });

        thread.start();
        thread.interrupt();
        System.out.println("接着执行...");
    }


    private static void test2() {
        Thread thread = new Thread(() -> {
            while (true) {
                Thread.yield();
                //响应中断,然后返回
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("当前线程被中断...");
                    return;
                }
            }
        });

        thread.start();
        thread.interrupt();
        System.out.println("接着执行...");
    }

    /**
     * Thread.sleep(3000)被中断后会清除中断标记，所以循环会继续运行
     */
    private static void test3() {
        Thread thread = new Thread(() -> {
            while (true) {
                Thread.yield();
                //响应中断,然后返回
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("当前线程被中断...");
                    return;
                }

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    System.out.println("线程中断,程序退出");
                }

            }
        });

        thread.start();
        thread.interrupt();
        System.out.println("接着执行...");
    }

    /**
     * Thread.sleep(3000)被中断后会清除中断标记，
     */
    private static void test4() {
        Thread thread = new Thread(() -> {
            while (true) {
                Thread.yield();
                //响应中断,然后返回
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("当前线程被中断...");
                    return;
                }

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    System.out.println("线程中断,程序退出");
                    Thread.currentThread().interrupt();
                }

            }
        });

        thread.start();
        thread.interrupt();
        System.out.println("接着执行...");
    }

    private static void test5() {
        final Object o1 = new Object();

        Thread thread1 = new Thread(() -> {
            System.out.println("t1 enter");
            synchronized (o1) {
                try {
                    System.out.println("start lock t1");
                    Thread.sleep(20000);
                    System.out.println("end lock t1");
                } catch (InterruptedException e) {
                    System.out.println("t1 interruptedException");
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            System.out.println("t2 enter");
            synchronized (o1) {
                try {
                    System.out.println("start lock t2");
                    Thread.sleep(1000);
                    System.out.println("end lock t2");
                } catch (InterruptedException e) {
                    System.out.println("t2 interruptedException");
                    e.printStackTrace();
                }
            }
        });

        thread1.start();
        thread2.start();

        // 主线程休眠一下，让t1,t2线程百分百已经启动，避免线程交替导致测试结果混淆
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 中断t2线程的执行
        thread2.interrupt();
        System.out.println("t2 interrupt...");
    }

}
