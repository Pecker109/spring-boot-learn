package consumer.model.config;

import consumer.model.handler.event.MailEventHandler;
import consumer.model.handler.work.MailWorkHandler;
import org.springframework.stereotype.Service;

/**
 * @author Pecker
 * @since 2025/6/19 21:08
 */
@Service
public class TestDisruptor9 extends AbstractDisruptorFactory {
    @Override
    protected void disruptorOperate() {
        MailWorkHandler c1 = new MailWorkHandler();
        MailWorkHandler c2 = new MailWorkHandler();
        MailEventHandler c3 = new MailEventHandler();
        MailEventHandler c4 = new MailEventHandler();
        MailEventHandler c5 = new MailEventHandler();

        /*
                      _ _ _ _
                     | -->c1 |        -->c3
         RingBuffer  |       | -->           -->c5
                     | -->c2 |        -->c4
                      _ _ _ _
         */

        disruptor
                // C1和C2共同消费,sequence[0] 只会被其中一个消费
                .handleEventsWithWorkerPool(c1, c2)
                // C3和C4独立消费，C3 C4 都会消费 sequence[0] ,但C3 C4都依赖C1或C2 消费完 sequence[0]
                .then(c3, c4)
                // C5 独立消费,依赖C3和C4 都消费完 sequence[0]
                .then(c5);
    }
}