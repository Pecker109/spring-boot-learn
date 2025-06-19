package com.example.demo.multiThread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionExample {
    private final Lock lock = new ReentrantLock();
    private final Condition conditionA = lock.newCondition();
    private final Condition conditionB = lock.newCondition();
    private boolean flagA = false;
    private boolean flagB = false;

    public void waitForA() throws InterruptedException {
        lock.lock();
        System.out.println("1 获取到锁");
        try {
            while (!flagA) {
                System.out.println("等待条件 A");
                /*
                调用 AQS.ConditionObject
                1.当前线程绑定 Node 节点
                2.Node 节点加入队列,waitStatus = Node.CONDITION
                3.阻塞当前线程
                 */
                conditionA.await(); // 等待条件 A
            }
            System.out.println("Condition A is ok");
        } finally {
            System.out.println("1 释放锁");
            lock.unlock();
        }
    }

    public void waitForB() throws InterruptedException {
        lock.lock();
        System.out.println("2 获取到锁");
        try {
            while (!flagB) {
                System.out.println("等待条件 B");
                conditionB.await(); // 等待条件 B
            }
            System.out.println("Condition B is ok");
        } finally {
            lock.unlock();
            System.out.println("2 释放锁");
        }
    }

    public void setFlagA() {
        lock.lock();
        System.out.println("3 获取到锁");
        try {
            flagA = true;
            /*
            调用 AQS.ConditionObject.signal()方法
            1.找到条件队列中的头节点
            2.获取头结点对应的线程,唤醒线程
            3.更新队列
             */
            conditionA.signal(); // 通知等待条件 A 的线程
            System.out.println("Flag A is set");
        } finally {
            lock.unlock();
            System.out.println("3 释放锁");
        }
    }

    public void setFlagB() {
        lock.lock();
        System.out.println("4 获取到锁");
        try {
            flagB = true;
            conditionB.signal(); // 通知等待条件 B 的线程
            System.out.println("Flag B is set");
        } finally {
            lock.unlock();
            System.out.println("4 释放锁");
        }
    }

    public static void main(String[] args) {

        /*
        1.Condition 的核心方法
            void await()	使当前线程等待，直到被通知或中断。
            void awaitUninterruptibly()	使当前线程等待，直到被通知（不可中断）。
            long awaitNanos(long nanos)	使当前线程等待，直到被通知、中断或超时（纳秒级别）。
            boolean await(long time, TimeUnit unit)	使当前线程等待，直到被通知、中断或超时。
            boolean awaitUntil(Date deadline)	使当前线程等待，直到被通知、中断或到达指定时间。
            void signal()	唤醒一个等待的线程。
            void signalAll()	唤醒所有等待的线程。
        2.Condition 与 wait()/notify() 的对比
            特性: Condition <--> wait()/notify()
            锁的支持:必须与 Lock 配合使用 <--> 与 synchronized 配合使用
            多条件支持: 支持多个 Condition <--> 不支持
            超时等待: 支持 <--> 不支持
            可中断等待: 支持 <--> 支持
            灵活性: 更灵活 <--> 较简单
         */
        ConditionExample example = new ConditionExample();

        // 线程 1：等待条件 A
        Thread threadA = new Thread(() -> {
            try {
                example.waitForA();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        threadA.start();

        // 线程 2：等待条件 B
        Thread threadB = new Thread(() -> {
            try {
                example.waitForB();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        threadB.start();


        // 线程 3：设置条件 A
        Thread setterA = new Thread(() -> {
            try {
                Thread.sleep(1000); // 模拟延迟
                example.setFlagA();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        setterA.start();


        // 线程 4：设置条件 B
        Thread setterB = new Thread(() -> {
            try {
                Thread.sleep(2000); // 模拟延迟
                example.setFlagB();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        setterB.start();

        try {
            threadA.join();
            threadB.join();
            setterA.join();
            setterB.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All threads finished.");
    }
}