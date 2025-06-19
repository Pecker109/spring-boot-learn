package consumer.model.config;

import consumer.model.handler.event.MailEventHandler;
import org.springframework.stereotype.Service;

/**
 * @author Pecker
 * @since 2025/6/19 20:59
 */
@Service("testDisruptor5")
public class TestDisruptor5 extends AbstractDisruptorFactory {
    @Override
    protected void disruptorOperate() {
        MailEventHandler c1 = new MailEventHandler();
        MailEventHandler c2 = new MailEventHandler();
        MailEventHandler c3 = new MailEventHandler();

        /*
                   -->c1
        RingBuffer          -->c3
                   -->c2
        依赖程度为:c1 和 c2 都处理完sequence[0] 后 c3 开始处理 sequence[0]
         */

        disruptor
                // C1、C2独立消费
                .handleEventsWith(c1, c2)
                // C3独立消费,依赖C1和C2
                .then(c3);
    }
}
