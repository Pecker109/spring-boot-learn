package consumer.model.config;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import consumer.model.event.OrderEvent;
import consumer.model.event.OrderEventFactory;
import consumer.model.handler.event.MailEventHandler;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 多生产者模式
 *
 * @author Pecker
 * @since 2025/6/19 20:27
 */
@Service("multiProducerModeDisruptor")
public class MultiProducerModeDisruptor extends AbstractDisruptorFactory {

    @Override
    public Disruptor<OrderEvent> instanceDisruptor() {
        // 实例化
        disruptor = new Disruptor<>(
                new OrderEventFactory(),
                BUFFER_SIZE,
                new CustomizableThreadFactory("event-handler-"),
                // 生产类型是多生产者
                ProducerType.MULTI,
                // BlockingWaitStrategy是默认的等待策略
                new BlockingWaitStrategy());

        // 留给子类实现具体的事件消费逻辑
        disruptorOperate();

        // 启动
        disruptor.start();

        return disruptor;
    }

    @Override
    protected void disruptorOperate() {
        // 一号消费者
        MailEventHandler c1 = new MailEventHandler();

        // 二号消费者
        MailEventHandler c2 = new MailEventHandler();

        // handleEventsWith，表示创建的多个消费者以共同消费的模式消费
        disruptor.handleEventsWith(c1, c2);
    }
}
