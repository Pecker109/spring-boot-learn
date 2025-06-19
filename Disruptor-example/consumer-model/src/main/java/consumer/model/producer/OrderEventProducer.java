package consumer.model.producer;

import com.lmax.disruptor.RingBuffer;
import consumer.model.event.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Pecker
 * @since 2025/6/19 15:03
 */
@Slf4j
public class OrderEventProducer {

    public void onData(RingBuffer<OrderEvent> ringBuffer, String content) {
        log.info("OrderEventProducer 发布事件,content = {}", content);

        // ringBuffer是个队列，其next方法返回的是下最后一条记录之后的位置，这是个可用位置
        long sequence = ringBuffer.next();

        try {
            // sequence位置取出的事件是空事件
            OrderEvent orderEvent = ringBuffer.get(sequence);
            // 空事件添加业务信息
            orderEvent.setValue(content);
        } finally {
            // 发布
            // 发布
            log.info("RingBuffer 发布事件,RingBuffer next Sequence = {}.ringBuffer={}", sequence, ringBuffer);
            ringBuffer.publish(sequence);
        }
    }
}
