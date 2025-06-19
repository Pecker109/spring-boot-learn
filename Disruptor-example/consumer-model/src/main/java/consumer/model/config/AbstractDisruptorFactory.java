package consumer.model.config;

import com.lmax.disruptor.dsl.Disruptor;
import consumer.model.event.OrderEvent;
import consumer.model.event.OrderEventFactory;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import javax.annotation.PostConstruct;

/**
 * @author Pecker
 * @since 2025/6/19 15:11
 */
public abstract class AbstractDisruptorFactory {

    public static final int BUFFER_SIZE = 16;

    Disruptor<OrderEvent> disruptor;

    public Disruptor<OrderEvent> getDisruptor() {
        return disruptor;
    }

    @PostConstruct
    public Disruptor<OrderEvent> instanceDisruptor() {

        disruptor = new Disruptor<>(
                // 指定事件创建工厂
                new OrderEventFactory(),
                // 初始化 RingBuffer
                BUFFER_SIZE,
                // 绑定消费者线程
                new CustomizableThreadFactory("event-handler-"));

        // 留给子类实现具体的事件消费逻辑
        disruptorOperate();

        // 启动:启动消费者线程
        disruptor.start();

        return disruptor;
    }

    /**
     * 留给子类实现具体的事件消费逻辑
     */
    protected abstract void disruptorOperate();

}
