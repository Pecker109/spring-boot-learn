package low.level.operate.config;


import com.lmax.disruptor.*;
import common.event.StringEvent;
import common.event.StringEventFactory;
import common.handler.StringEventHandler;
import common.handler.StringWorkHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Pecker
 * @since 2025/6/10 16:22
 */
@Configuration
public class RingBufferConfig {
    /**
     * 环形缓冲区大小
     */
    private static final int BUFFER_SIZE = 16;

    /**
     * 消费者数量
     */
    private static final int CONSUMER_NUM = 3;

    @Bean(value = "oneConsumerRingBuffer")
    public RingBuffer<StringEvent> oneConsumerRingBuffer() {
        /*
        单一消费者消费模式:
        一个 RingBuffer 指定一个消费者:
        RingBuffer 中只开辟出一个 SequenceBarrier,
        SequenceBarrier 只关联一个 EventHandler
         */

        // 创建环形队列实例
        RingBuffer<StringEvent> ringBuffer = RingBuffer.createSingleProducer(new StringEventFactory(), BUFFER_SIZE);

        // 准备线程池
        ExecutorService executors = Executors.newFixedThreadPool(1);

        //创建SequenceBarrier
        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();

        // 创建事件处理的工作类，里面执行StringEventHandler处理事件
        BatchEventProcessor<StringEvent> batchEventProcessor = new BatchEventProcessor<>(
                ringBuffer,
                sequenceBarrier,
                new StringEventHandler());

        // 将消费者的sequence传给环形队列
        ringBuffer.addGatingSequences(batchEventProcessor.getSequence());

        // 在一个独立线程中取事件并消费
        executors.submit(batchEventProcessor);

        return ringBuffer;
    }

    @Bean(value = "multiConsumerRingBuffer")
    public RingBuffer<StringEvent> multiConsumerRingBuffer() {
          /*
          多个消费者广播消费模式(一条消息被所有消费者消费):
        一个 RingBuffer 指定3个消费者:
        RingBuffer 中开辟出 3 个 SequenceBarrier,
        每个 SequenceBarrier 关联一个 EventHandler
         */

        // 创建环形队列实例
        RingBuffer<StringEvent> ringBuffer = RingBuffer.createSingleProducer(new StringEventFactory(), BUFFER_SIZE);

        // 准备线程池,注意这里的线程池大小不再是 1
        ExecutorService executors = Executors.newFixedThreadPool(CONSUMER_NUM);

        // 创建多个消费者，并在独立线程中获取和消费事件
        for (int i = 0; i < CONSUMER_NUM; i++) {
            //创建SequenceBarrier
            SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();
            // 创建事件处理的工作类，里面执行StringEventHandler处理事件
            BatchEventProcessor<StringEvent> batchEventProcessor = new BatchEventProcessor<>(
                    ringBuffer,
                    sequenceBarrier,
                    new StringEventHandler());
            // 将消费者的sequence传给环形队列
            ringBuffer.addGatingSequences(batchEventProcessor.getSequence());

            // 每个 SequenceBarrier 会被分配一个线程
            executors.submit(batchEventProcessor);
        }
        return ringBuffer;
    }

    @Bean(value = "workerPoolRingBuffer")
    public RingBuffer<StringEvent> workerPoolRingBuffer() {
        /*
        1 个Producer 被 3 个 Consumer 分组消费:
        RingBuffer 中只开辟出 1 个 SequenceBarrier,
        SequenceBarrier 关联一个 WorkerPool,
        WorkerPool 关联 3 个WorkHandler,

         */

        // 创建环形队列实例
        RingBuffer<StringEvent> ringBuffer = RingBuffer.createSingleProducer(new StringEventFactory(), BUFFER_SIZE);

        // 准备线程池,注意这里的线程池大小不再是 1
        ExecutorService executors = Executors.newFixedThreadPool(CONSUMER_NUM);

        StringWorkHandler[] handlers = new StringWorkHandler[CONSUMER_NUM];

        // 创建多个StringWorkHandler实例，放入一个数组中
        for (int i = 0; i < CONSUMER_NUM; i++) {
            handlers[i] = new StringWorkHandler();
        }

        // 创建WorkerPool实例，将StringWorkHandler实例的数组传进去，代表共同消费者的数量
        WorkerPool<StringEvent> workerPool = new WorkerPool<>(ringBuffer, ringBuffer.newBarrier(), new IgnoreExceptionHandler(), handlers);

        // 这一句很重要，去掉就会出现重复消费同一个事件的问题
        //ringBuffer.addGatingSequences(workerPool.getWorkerSequences());

        workerPool.start(executors);

        return ringBuffer;
    }
}
