package consumer.model.config;

import consumer.model.handler.event.MailEventHandler;
import consumer.model.handler.work.MailWorkHandler;
import org.springframework.stereotype.Service;

/**
 * @author Pecker
 * @since 2025/6/19 21:08
 */
@Service
public class TestDisruptor10 extends AbstractDisruptorFactory {
    @Override
    protected void disruptorOperate() {
        MailEventHandler c1 = new MailEventHandler();
        MailEventHandler c2 = new MailEventHandler();
        MailWorkHandler c3 = new MailWorkHandler();
        MailWorkHandler c4 = new MailWorkHandler();
        MailEventHandler c5 = new MailEventHandler();

        /*
                                    _ _ _ _
                      -->c1       |  -->c3 |
         RingBuffer          -->  |        | -->c5
                      -->c2       |  -->c4 |
                                   _ _ _ _
         */

        disruptor
                // C1和C2独立消费,都会消费 sequence[0]
                .handleEventsWith(c1, c2)
                // C3和C4是共同消费，sequence[0] 只会被其中一个消费,但C3或C4 依赖C1和C2都执行完 sequence[0]
                .thenHandleEventsWithWorkerPool(c3, c4)
                // C5依赖C3或C4 执行完 sequence[0]
                .then(c5);
    }
}