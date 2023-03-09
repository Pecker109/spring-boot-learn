package com.example.demo.multiThread;

/**
 * @author Pecker
 * @Description 守护线程和非守护线程
 * @since 2023-02-21
 */
public class DaemonThread {


    public static void main(String[] args) {
        // 守护线程会随着 JVM 的结束而立马结束线程的任务
        // 非守护线程会等该线程的任务执行完之后才会随 JVM 结束
        DaemonTask task = new DaemonTask();
        Thread thread = new Thread(task);
        // thread.setDaemon(true) 守护线程,程序结束,线程也立马结束运行
        // thread.setDaemon(false) 非守护线程,即使程序要结束,非守护线程尚未结束,要等到该线程结束之后,程序才会结束
        thread.setDaemon(true);
        thread.start();
    }


    public static class DaemonTask implements Runnable {
        @Override
        public void run() {

            try {
                System.out.println("do something");
                Thread.sleep(5000);
                System.out.println("game over");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
