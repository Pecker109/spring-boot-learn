package consumer.model.config;

import consumer.model.handler.event.MailEventHandler;
import consumer.model.handler.event.SmsEventHandler;
import org.springframework.stereotype.Service;

/**
 * 独立的消费者
 * @author Pecker
 * @since 2025/6/19 15:45
 */
@Service("independentModeDisruptor")
public class IndependentModeDisruptor extends AbstractDisruptorFactory {
    @Override
    protected void disruptorOperate() {
        // 调用handleEventsWith，表示创建的多个消费者，每个都是独立消费的
        // 这里创建两个消费者，一个是短信的，一个是邮件的
        disruptor.handleEventsWith(new SmsEventHandler(), new MailEventHandler());
    }
}
