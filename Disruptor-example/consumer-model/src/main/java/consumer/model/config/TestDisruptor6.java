package consumer.model.config;

import consumer.model.handler.event.MailEventHandler;
import org.springframework.stereotype.Service;

/**
 * @author Pecker
 * @since 2025/6/19 21:08
 */
@Service("testDisruptor6")
public class TestDisruptor6 extends AbstractDisruptorFactory {
    @Override
    protected void disruptorOperate() {
        MailEventHandler c1 = new MailEventHandler();
        MailEventHandler c2 = new MailEventHandler();
        MailEventHandler c3 = new MailEventHandler();
        MailEventHandler c4 = new MailEventHandler();

        /*
                           -->c2
         RingBuffer -->c1          -->c4
                           -->c3
        依赖关系为:C1 执行完才会执行 C2 和 C3,C2 和 C3 之间没有固定顺序,C2和 C3 都执行完才会执行 C4.
        依赖关系是指同一 sequence[x],比如 sequence[0]
         */

        disruptor
                // C1 独立消费
                .handleEventsWith(c1)
                // C2和C3也独立消费,依赖 C1
                .then(c2, c3)
                // C4独立消费,依赖C2和C3
                .then(c4);
    }
}