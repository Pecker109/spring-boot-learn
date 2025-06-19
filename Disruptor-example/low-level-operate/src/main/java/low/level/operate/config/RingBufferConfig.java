package low.level.operate.config;


import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import common.event.StringEvent;
import common.event.StringEventFactory;
import common.handler.StringEventHandler;
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
    private static final int BUFFER_SIZE = 16;

    @Bean
    public RingBuffer<StringEvent> ringBuffer() {


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
}
