package com.example.demo.multiThread;

/**
 * @author Pecker
 * @Description 线程中断测试
 * @since 2021-04-22
 */
public class InterruptTest {
    /**
     * 作用1:
     * Thread.interrupt() 是 Java 中用于中断线程的方法。
     * 它通过设置线程的中断状态（interrupt status）来通知线程应该停止当前的操作。
     * 需要注意的是，interrupt() 方法并不会直接终止线程，而是通过设置中断状态，让线程自行决定如何处理中断。
     * 作用 2:
     * 唤醒阻塞的线程：
     * 如果线程因为调用 Object.wait()、Thread.join() 或 Thread.sleep() 等方法而处于阻塞状态，
     * interrupt() 会抛出 InterruptedException，并清除中断状态。
     * 如果需要保留中断状态，需要在捕获异常后重新设置中断状态:重新调用 interrupt()
     * 3.中断状态检查:
     * 可以通过 Thread.interrupted() 或 Thread.isInterrupted() 检查中断状态。
     * Thread.interrupted() 会清除中断状态，而 Thread.isInterrupted() 不会
     * 4.中断处理的两种方式:
     * a.针对于程序中调用了 sleep(),wait() 等方法的中断处理是会抛出异常 InterruptedException,但是要注意的是抛出InterruptedException 异常之后 中断状态会被清除掉
     * b.另外一种中断处理方式是通过程序中手动判断 isInterrupted() 去处理
     *
     * @param args
     */
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
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    System.out.println("线程中断,程序退出");
                }

                //响应中断,然后返回
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("当前线程被中断...");
                    return;
                } else {
                    System.out.println("中断清除,继续执行");
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
