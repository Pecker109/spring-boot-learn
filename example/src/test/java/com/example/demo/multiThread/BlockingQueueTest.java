package com.example.demo.multiThread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class BlockingQueueTest {

    public static void main(String[] args) {

        /*
        1.BlockingQueue 的特点:
            a.线程安全：
                BlockingQueue 的实现类是线程安全的，无需额外的同步措施。
                如何实现线程安全的?内部通过 ReentrantLock + AQS.ConditionObject实现,
                ReentrantLock保证线程安全 , AQS.ConditionObject保证阻塞
                AQS.ConditionObject 用法可参照 ConditionExample
            b.阻塞操作：
                当队列为空时，获取操作会阻塞，直到队列中有元素。
                当队列满时，插入操作会阻塞，直到队列有空闲空间。
            c.容量限制：
                BlockingQueue 可以是有界队列（固定容量）或无界队列（无限容量）。
            d.支持超时：
                提供带超时的插入和获取操作，避免无限等待。
        2. BlockingQueue 的核心方法
            void put(E e)	将元素插入队列，如果队列满则阻塞，直到有空闲空间。
            E take()	获取并移除队列头部的元素，如果队列为空则阻塞，直到有元素可用。
            boolean offer(E e)	将元素插入队列，如果队列满则立即返回 false。
            boolean offer(E e, long timeout, TimeUnit unit)	将元素插入队列，如果队列满则等待指定时间，超时后返回 false。
            E poll()	获取并移除队列头部的元素，如果队列为空则立即返回 null。
            E poll(long timeout, TimeUnit unit)	获取并移除队列头部的元素，如果队列为空则等待指定时间，超时后返回 null。
            E peek()	获取队列头部的元素但不移除，如果队列为空则返回 null。
            int remainingCapacity()	返回队列的剩余容量。
            int size()	返回队列中的元素数量。
        3. BlockingQueue 的实现类
            ArrayBlockingQueue	基于数组的有界阻塞队列。
            LinkedBlockingQueue	基于链表的阻塞队列，默认无界，也可以设置容量。
            PriorityBlockingQueue	支持优先级排序的无界阻塞队列。
            DelayQueue	存储延迟元素的阻塞队列，元素只有在其延迟到期后才能被取出。
            SynchronousQueue	不存储元素的阻塞队列，每个插入操作必须等待一个对应的移除操作。
         */

        int bound = 10;
        int producerNums = 16;
        // 可用于Java虚拟机的处理器数量(CPU 核数)
        int processorNums = Runtime.getRuntime().availableProcessors();

        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(bound);
        // 生产者
        for (int i = 1; i < producerNums; i++) {
            new Thread(new NumbersProducer(queue)).start();
        }
        // 消费者
        for (int j = 0; j < processorNums; j++) {
            new Thread(new NumbersConsumer(queue)).start();
        }
    }

    @Slf4j
    public static class NumbersProducer implements Runnable {
        private BlockingQueue<Integer> numbersQueue;

        public NumbersProducer(BlockingQueue<Integer> numbersQueue) {
            this.numbersQueue = numbersQueue;
        }

        public void run() {
            try {
                generateNumbers();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        private void generateNumbers() throws InterruptedException {
            for (int i = 0; i < 10; i++) {
                int num = ThreadLocalRandom.current().nextInt(100);
                log.info("线程-{}号,生产消息:{}", Thread.currentThread().getId(), num);
                numbersQueue.put(num);
            }
        }
    }

    @Slf4j
    public static class NumbersConsumer implements Runnable {
        private BlockingQueue<Integer> queue;

        public NumbersConsumer(BlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        public void run() {
            try {
                while (true) {
                    Integer number = queue.take();
                    Thread.sleep(100);
                    log.info("消费者线程-{}号,消费消息-编号:{}", Thread.currentThread().getId(), number);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}