package consumer.model.config;

import consumer.model.handler.event.MailEventHandler;
import consumer.model.handler.work.MailWorkHandler;
import org.springframework.stereotype.Service;

/**
 * @author Pecker
 * @since 2025/6/19 21:08
 */
@Service
public class TestDisruptor8 extends AbstractDisruptorFactory {
    @Override
    protected void disruptorOperate() {
        MailWorkHandler c1 = new MailWorkHandler();
        MailWorkHandler c2 = new MailWorkHandler();
        MailWorkHandler c3 = new MailWorkHandler();
        MailWorkHandler c4 = new MailWorkHandler();
        MailWorkHandler c5 = new MailWorkHandler();

        /*
                      _ _ _ _        _ _ _ _
                     | -->c1 |      |  -->c3 |
         RingBuffer  |       | -->  |        | -->c5
                     | -->c2 |      |  -->c4 |
                      _ _ _ _        _ _ _ _
         */
        disruptor
                // C1和C2是共同消费.sequence[0] 只会被其中一个消费
                .handleEventsWithWorkerPool(c1, c2)
                // C3和C4是共同消费，sequence[0] 只会被其中一个消费.但C3,C4 依赖 C1或C2 执行完 sequence[0]
                .thenHandleEventsWithWorkerPool(c3, c4)
                // 然后C5依赖C3或C4执行完 sequence[0]
                .thenHandleEventsWithWorkerPool(c5);
    }
}