package consumer.model.config;

import consumer.model.handler.event.SmsEventHandler;
import consumer.model.handler.work.MailWorkHandler;
import org.springframework.stereotype.Service;

/**
 * @author Pecker
 * @since 2025/6/19 20:15
 */
@Service("shareAndIndependentModeDisruptor")
public class ShareAndIndependentModeDisruptor extends AbstractDisruptorFactory{
    @Override
    protected void disruptorOperate() {
        // 调用handleEventsWith，表示创建的多个消费者，每个都是独立消费的
        // 这里创建一个消费者，短信服务
        disruptor.handleEventsWith(new SmsEventHandler());

        // mailWorkHandler1模拟一号邮件服务器
        MailWorkHandler mailWorkHandler1 = new MailWorkHandler();

        // mailWorkHandler2模拟一号邮件服务器
        MailWorkHandler mailWorkHandler2 = new MailWorkHandler();

        // 调用handleEventsWithWorkerPool，表示创建的多个消费者以共同消费的模式消费
        disruptor.handleEventsWithWorkerPool(mailWorkHandler1, mailWorkHandler2);
    }
}
