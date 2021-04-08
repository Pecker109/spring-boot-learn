package com.example.demo;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2020-11-12
 */
public class ReentrantLockTest {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();

        // 加锁
        lock.lock();
        try {
            // 2. 执行临界区代码
        } finally {
            // 3. 解锁
            lock.unlock();
        }
    }
}
