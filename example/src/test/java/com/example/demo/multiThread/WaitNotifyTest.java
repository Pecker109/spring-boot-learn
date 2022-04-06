package com.example.demo.multiThread;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Pecker
 * @Description wait 和 notify 用法
 * @since 2022-01-08
 */
public class WaitNotifyTest {

    public static void main(String[] args) throws InterruptedException {
        TaskQueue q = new TaskQueue();
        List<Thread> ts = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Thread t = new Thread(() -> {
                // 执行task:
                while (true) {
                    try {
                        String s = q.getTask();
                        System.out.println("execute task: " + s);
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            });
            t.start();
            ts.add(t);
        }


        Thread add = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                // 放入task:
                String s = "t-" + Math.random();
                System.out.println("add task: " + s);
                q.addTask(s);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    //
                }
            }
        });
        add.start();
        add.join();
        System.out.println("主线程...");
        Thread.sleep(100);

        for (Thread t : ts) {
            t.interrupt();
        }
    }
}

class TaskQueue {
    Queue<String> queue = new LinkedList<>();

    public synchronized void addTask(String s) {
        this.queue.add(s);
        // 通知所有线程
        this.notifyAll();
    }

    public synchronized String getTask() throws InterruptedException {
        while (queue.isEmpty()) {
            // 队列为空线程会被挂起
            this.wait();
        }
        return queue.remove();
    }

}
