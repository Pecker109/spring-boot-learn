package consumer.model.event;

import com.lmax.disruptor.EventFactory;

/**
 * @author Pecker
 * @since 2025/6/19 15:13
 */
public class OrderEventFactory implements EventFactory<OrderEvent> {
    @Override
    public OrderEvent newInstance() {
        return new OrderEvent();
    }
}
