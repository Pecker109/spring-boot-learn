package com.example.demo.leetcode;

import java.util.concurrent.Semaphore;

/**
 * @author Pecker
 * @Description 交替打印线程
 * @since 2021-04-17
 */
public class JiaoTiPrintTest {

    /**
     * 实现思路:
     * 一个线程对应一个信号量对象  t0 -> t1 -> t2 -> t0  :  [s0 , s1 , s2]
     * <p>
     * 要解决的问题
     * 1.线程如何被阻塞: 靠 semaphores#acquire() 方法
     * 2.保证从 t0 线程开始执行 : 初始化信号量数组就阻塞住非 t0 线程
     * 3.如何保证本线程执行完才去释放下一个线程的信号量 : 所有线程都被阻塞在 acquire(),获取到信号量之后才去执行输出逻辑,最后执行完释放下一个信号量
     */


    /*
     1.线程关联信号量
     2.线程获取到信号量才能执行输出逻辑
     3.保证线程执行的顺序性,当前线程执行完后才去释放下一个线程的信号量
     4.保证第一个线程先执行
     */

    private static final int THREAD_COUNT = 3;
    private static final int STOP_FLAG = 100;
    private static volatile int count = 0;

    public static void main(String[] args) throws InterruptedException {
        // 创建信号量数组
        Semaphore[] semaphores = new Semaphore[THREAD_COUNT];
        for (int i = 0; i < THREAD_COUNT; i++) {
            semaphores[i] = new Semaphore(1);

            // 保证从 0 开始, 其他线程需要被阻塞住,所以要先占用着其他两个线程的信号量
            if (i != 0) {
                semaphores[i].acquire();
            }
        }

        for (int i = 0; i < THREAD_COUNT; i++) {
            final int index = i;
            new Thread(new Runnable() {
                // 线程和信号量绑定
                final Semaphore currSemaphore = semaphores[index];
                final Semaphore nextSemaphore = semaphores[index == THREAD_COUNT - 1 ? 0 : index + 1];

                @Override
                public void run() {
                    final String threadName = Thread.currentThread().getName();

                    while (true) {
                        try {
                            // 线程操作被阻塞在 acquire() 处,release() 之后才会往下执行
                            // 这样做其实有个问题,第一次循环所有的线程都会被阻塞在这里,直到最后一个线程唤醒第一个线程,而不是线程一进来就执行输出逻辑
                            currSemaphore.acquire();
                            // 何时终止打印
                            if (count > STOP_FLAG) return;
                            System.out.println(threadName + count);
                            count += 1;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            nextSemaphore.release();
                        }
                    }
                }
            }, "线程" + i + ": ").start();
        }
    }

}
