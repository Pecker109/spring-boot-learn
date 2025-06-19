package consumer.model.config;

import consumer.model.handler.work.MailWorkHandler;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;

/**
 * 共享的消费者
 * @author Pecker
 * @since 2025/6/19 20:00
 */
@Service("shareModeDisruptor")
public class ShareModeDisruptor extends AbstractDisruptorFactory {
    @Override
    protected void disruptorOperate() {
        // mailWorkHandler1模拟一号邮件服务器
        MailWorkHandler mailWorkHandler1 = new MailWorkHandler();

        // mailWorkHandler2模拟一号邮件服务器
        MailWorkHandler mailWorkHandler2 = new MailWorkHandler();

        // 调用handleEventsWithWorkerPool，表示创建的多个消费者以共同消费的模式消费
        disruptor.handleEventsWithWorkerPool(mailWorkHandler1, mailWorkHandler2);
    }
}
