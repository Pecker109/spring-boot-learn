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
     *
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
    private static volatile int count = 0;

    public static void main(String[] args) throws InterruptedException {
        Semaphore[] semaphores = new Semaphore[THREAD_COUNT];
        for (int i = 0; i < THREAD_COUNT; i++) {
            semaphores[i] = new Semaphore(1);

            // 保证从 0 开始, 其他线程需要被阻塞住,所以要先占用着其他两个线程的信号量
//            if (i != THREAD_COUNT -1) {
//                semaphores[i].acquire();
//            }
            if (i != 0){
                semaphores[i].acquire();
            }
        }

        for (int i = 0; i < THREAD_COUNT; i++) {
            final int index = i;
            new Thread(new Runnable() {
                //final Semaphore preSemaphore = semaphores[index == 0 ? THREAD_COUNT - 1 : index - 1];
                final Semaphore currSemaphore = semaphores[index];
                final Semaphore nextSemaphore = semaphores[index == THREAD_COUNT - 1 ? 0 : index + 1];

                @Override
                public void run() {
                    final String threadName = Thread.currentThread().getName();

                    while (true) {
                        try {
                            currSemaphore.acquire();
                            //preSemaphore.acquire();
                            if (count > 10 * THREAD_COUNT) return;
                            System.out.println(threadName + count++);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            //currSemaphore.release();
                            nextSemaphore.release();
                        }
                    }
                }
            }, "线程" + i + ": ").start();
        }
    }

}
