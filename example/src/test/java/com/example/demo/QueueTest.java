package com.example.demo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2022-07-15
 */
@Slf4j
public class QueueTest {


    public static void main(String[] args) {
        // 无界线程安全队列
        // concurrentLinkedQueue();

        // 阻塞队列
        //blockingQueue();

        // 延迟队列
        delayQueue();
    }


    @SneakyThrows
    public static void blockingQueue() {
        // 阻塞队列阻塞的方法是 put 和 take 方法
        // 创建一个长度为 5 的阻塞队列
        ArrayBlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue(5);

        // 新创建一个线程执行入列
        new Thread(() -> {
            // 循环 10 次
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                    // 这里用 put 会阻塞等待把消息放进去,用 offer 消息就会丢失,所以 offer 返回值是个 boolean 类型
                    blockingQueue.put(i);
                    // blockingQueue.offer(i);
                    log.info(" put message success : {}", i);
                } catch (Exception e) {
                    log.error(e.getMessage());
                }

            }
            log.info(" put thread end...");
        }).start();

        // 新创建一个线程执行出列
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    // 休眠 1S
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                }
                if (!blockingQueue.isEmpty()) {
                    try {
                        // 出列 take 会阻塞,知道获取到结果,pool 会取到空值
                        // Integer poll = blockingQueue.poll();
                        int message = blockingQueue.take();
                        log.info("get a message : {}", message);
                    } catch (InterruptedException e) {
                        log.error(e.getMessage());
                    }
                }

            }
        }).start();

        Thread.sleep(20 * 1000);
    }

    /**
     * 无界线程安全队列
     */
    static ConcurrentLinkedQueue<Integer> quoteQueue = new ConcurrentLinkedQueue<>();

    static void concurrentLinkedQueue() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        // 生产者
        executorService.submit(new Provider("生产者 1 : "));
        executorService.submit(new Provider("生产者 2 : "));
        executorService.submit(new Provider("生产者 3 : "));

        // 消费者
        executorService.submit(new Consumer("消费者 1 : "));
        //executorService.submit(new Consumer("消费者 2 : "));
        //executorService.submit(new Consumer("消费者 3 : "));
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Provider implements Runnable {
        private String name;

        @SneakyThrows
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println(name + i);
                quoteQueue.add(i);
                //Thread.sleep(20);
            }
        }
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Consumer implements Runnable {
        private String name;

        @SneakyThrows
        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                if (quoteQueue.isEmpty()) {
                    System.out.println("队列为空");
                } else {
                    System.out.println("==============" + name + quoteQueue.poll());
                }
            }
        }
    }


    static void delayQueue() {
        // 创建延迟消息队列
        DelayQueue<DelayMessage> delayQueue = new DelayQueue<>();
        // 创建并启动延迟队列的消费者线程
        new Thread(new DelayQueueConsumer(delayQueue)).start();
        // 执行测试样例1
        // test1(delayQueue);
        // 执行测试样例2
        // test2(delayQueue);
        // 执行测试样例3
        test3(delayQueue);
    }


    /**
     * 测试用例1：生成5条TTL时间依次增大的延迟消息：1秒，2秒，3秒，4秒，5秒
     *
     * @param delayQueue 延迟队列
     */
    static void test1(DelayQueue<DelayMessage> delayQueue) {
        for (int i = 1; i <= 5; i++) {
            DelayMessage delayMessage = new DelayMessage(String.valueOf(i), i * 1000L);
            log.info("Producer publish message: {}", String.valueOf(i));
            delayQueue.offer(delayMessage);
        }
    }

    /**
     * 测试用例2：生成5条TTL时间依次减小的延迟消息：5秒，4秒，3秒，2秒，1秒
     * @param delayQueue 延迟队列
     */
    private static void test2(DelayQueue<DelayMessage> delayQueue) {
        for (int i = 5; i > 0; i--) {
            String message = String.valueOf(i);
            DelayMessage delayMessage = new DelayMessage(message, i*1000L);
            log.info("Producer publish message: {}", message);
            delayQueue.offer(delayMessage);
        }
    }

    /**
     * 测试用例3：生成5个延迟时间随机的延迟消息
     * @param delayQueue 延迟队列
     */
    private static void test3(DelayQueue<DelayMessage> delayQueue) {
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            // 生成1~10的随机数：作为1秒-10秒的延迟时间
            int ttl = 1 + random.nextInt(10);
            String message = String.valueOf(ttl);
            DelayMessage delayMessage = new DelayMessage(message, ttl*1000L);
            log.info("Producer publish message: {}", message);
            delayQueue.offer(delayMessage);
        }
    }

    /**
     * 自定义延迟队列
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DelayMessage implements Delayed {

        /**
         * 延迟任务中的任务数据
         */
        private String message;
        /**
         * 延迟任务到期时间（过期时间）
         */
        private long ttl;


        @Override
        public long getDelay(TimeUnit unit) {
            // 计算该任务距离过期还剩多少时间
            long remaining = ttl - System.currentTimeMillis();
            return unit.convert(remaining, TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            // 比较、排序：对任务的延时大小进行排序，将延时时间最小的任务放到队列头部
            return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
        }
    }


    public static class DelayQueueConsumer implements Runnable {

        private final DelayQueue<DelayMessage> delayQueue;

        /**
         * 构造函数
         *
         * @param delayQueue 延迟队列
         */
        public DelayQueueConsumer(DelayQueue<DelayMessage> delayQueue) {
            this.delayQueue = delayQueue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    // 从延迟队列的头部获取已经过期的消息
                    // 如果暂时没有过期消息或者队列为空，则take()方法会被阻塞，直到有过期的消息为止
                    DelayMessage delayMessage = delayQueue.take();
                    log.info("Consumer received message: {}", delayMessage.getMessage());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
