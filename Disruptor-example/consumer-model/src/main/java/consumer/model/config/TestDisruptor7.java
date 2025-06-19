package consumer.model.config;

import consumer.model.handler.event.MailEventHandler;
import org.springframework.stereotype.Service;

/**
 * @author Pecker
 * @since 2025/6/19 21:08
 */
@Service
public class TestDisruptor7 extends AbstractDisruptorFactory {
    @Override
    protected void disruptorOperate() {
        MailEventHandler c1 = new MailEventHandler();
        MailEventHandler c2 = new MailEventHandler();
        MailEventHandler c3 = new MailEventHandler();
        MailEventHandler c4 = new MailEventHandler();
        MailEventHandler c5 = new MailEventHandler();

        /*
                      -->c1         -->c3
         RingBuffer           -->           -->c5
                      -->c2         -->c4
         */

        disruptor
                // C1和C2独立消费,C1 和 C2间没有先后
                .handleEventsWith(c1, c2)
                // C3和C4也是独立消费，但C3和C4依赖C1和C2都执行完,C3 和 C4间没有先后
                .then(c3, c4)
                // 然后C5依赖C3和C4都执行完
                .then(c5);
    }
}