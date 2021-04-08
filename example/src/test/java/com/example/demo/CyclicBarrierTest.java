package com.example.demo;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Pecker
 * @Description 测试 CyclicBarrier 类中带参数的 await() 方法
 * @since 2021-03-16
 */
public class CyclicBarrierTest {
    // 请求的数量
    private static final int threadCount = 550;
    // 需要同步的线程数量
    private static final CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> {
        System.out.println("------当线程数达到之后，优先执行------\n\n");
    });

    public static void main(String[] args) throws InterruptedException {
        // 创建线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(10);

        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            Thread.sleep(1000);
            threadPool.execute(() -> {
                try {
                    test(threadNum);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        threadPool.shutdown();
    }

    public static void test(int threadnum) throws InterruptedException, BrokenBarrierException {
        System.out.println("threadnum:" + threadnum + "is ready");
        /*
         * 线程也会阻塞在 await() 方法处
         * 相比 CountDownLatch 没有手动调用 countDown() 方法
         * cyclicBarrier 内部自己会判断,线程数当达到 parties 后会执行 await() 后面的代码
         */
        cyclicBarrier.await();
        System.out.println("threadnum:" + threadnum + "is finish");
    }


}
