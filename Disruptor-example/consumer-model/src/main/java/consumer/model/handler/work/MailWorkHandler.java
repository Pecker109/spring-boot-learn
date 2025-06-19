package consumer.model.handler.work;

import com.lmax.disruptor.WorkHandler;
import consumer.model.event.OrderEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * 共同消费的 Disruptor 需要 WorkHandler
 *
 * @author Pecker
 * @since 2025/6/19 20:06
 */
@Slf4j
public class MailWorkHandler implements WorkHandler<OrderEvent> {
    @Override
    public void onEvent(OrderEvent event) throws Exception {
        log.info("共同消费模式的邮件服务 : {}", event);

        // 这里延时100ms，模拟消费事件的逻辑的耗时
        Thread.sleep(100);

    }
}
