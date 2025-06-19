package consumer.model.service;

import com.lmax.disruptor.RingBuffer;
import consumer.model.event.OrderEvent;
import consumer.model.producer.OrderEventProducer;
import org.springframework.stereotype.Component;

/**
 * @author Pecker
 * @since 2025/6/19 15:00
 */
@Component
public class ConsumeModeService {

    /**
     * 发布一个事件
     *
     * @param value
     * @return
     */
    public void publish(OrderEventProducer producer, RingBuffer<OrderEvent> ringBuffer, String value) {
        producer.onData(ringBuffer, value);
    }

}
