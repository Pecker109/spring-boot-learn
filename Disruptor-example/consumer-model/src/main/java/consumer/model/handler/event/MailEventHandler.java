package consumer.model.handler.event;

import com.lmax.disruptor.EventHandler;
import consumer.model.event.OrderEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * 模拟邮件服务
 *
 * @author Pecker
 * @since 2025/6/19 14:55
 */
@Slf4j
public class MailEventHandler implements EventHandler<OrderEvent> {
    @Override
    public void onEvent(OrderEvent event, long sequence, boolean endOfBatch) throws Exception {
        log.info("邮件服务 sequence [{}], endOfBatch [{}], event : {}", sequence, endOfBatch, event);

        // 这里延时100ms，模拟消费事件的逻辑的耗时
        Thread.sleep(100);

    }
}
