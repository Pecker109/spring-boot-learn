package basic.event.config;


import com.lmax.disruptor.dsl.Disruptor;
import common.event.StringEvent;
import common.event.StringEventFactory;
import common.handler.StringEventHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

/**
 * @author Pecker
 * @since 2025/6/10 16:22
 */
@Configuration
public class DisruptorConfig {
    private static final int BUFFER_SIZE = 16;

    @Bean
    public Disruptor<StringEvent> disruptor() {
        /*
        初始化 Disruptor 主要做哪些事情:
        1.指定事件创建工厂
        2.初始化 RingBuffer
        3.绑定消费者线程
        4.关联处理类:RingBuffer 内部通过栅栏 SequenceBarrier,来判断EventHandler 如何分发RingBuffer中的 Event
        5.启动:启动消费者线程
        low-level-operate.RingBufferConfig 中做的事情和这里做的事情其实是一样的,
        DisruptorConfig 这里是通过初始化Disruptor 隐藏了 RingBuffer 内部初始化逻辑;
        RingBufferConfig 是直接初始化 RingBuffer 来直观的了解内部是如何初始化的.
         */
        Disruptor<StringEvent> disruptor = new Disruptor<>(
                // 指定事件创建工厂
                new StringEventFactory(),
                // 初始化 RingBuffer
                BUFFER_SIZE,
                // 绑定消费者线程
                new CustomizableThreadFactory("event-handler-"));

        // 关联处理类
        // RingBuffer 会先按分栅栏 SequenceBarrier,再在每个栅栏中按 handlers 指定 Sequence.handleEventsWith()方法只有一个栅栏
        disruptor.handleEventsWith(new StringEventHandler());

        // 启动:启动消费者线程
        disruptor.start();

        return disruptor;
    }
}
