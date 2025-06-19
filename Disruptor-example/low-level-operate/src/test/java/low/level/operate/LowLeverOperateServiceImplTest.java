package low.level.operate;

import lombok.extern.slf4j.Slf4j;
import low.level.operate.service.LowLevelOperateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Pecker
 * @since 2025/6/11 14:44
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LowLeverOperateServiceImplTest {
    @Autowired
    @Qualifier("oneConsumer")
    LowLevelOperateService oneConsumer;

    @Autowired
    @Qualifier("multiConsumer")
    LowLevelOperateService multiConsumer;

    @Autowired
    @Qualifier("workerPoolConsumer")
    LowLevelOperateService workerPoolConsumer;
    private static final int EVENT_COUNT = 100;

    @Test
    public void testOneConsumer() throws InterruptedException {
        /*
        消息被广播消费还是被分组消费取决于 SequenceBarrier 关联多少 Processor
        1 个Producer 被 1 个 Consumer 消费: 只有一个 SequenceBarrier 关联 1 个 Processor
        1 个Producer 被 3 个 Consumer 广播消费: 3 个 SequenceBarrier ,每个 SequenceBarrier 关联 1 个 Processor
        1 个Producer 被 3 个 Consumer 分组消费: 只有一个 SequenceBarrier,这个SequenceBarrier关联多个 Processor

        独立消费的每个消费者都有属于自己独有的SequenceBarrier实例，共同消费者是所有人共用同一个SequenceBarrier实例
        独立消费的每个消费者都有属于自己独有的Sequence实例，对于共同消费者，虽然他们也有属于自己的Sequence实例，但这个Sequence实例的值是从一个公共Sequence实例（WorkerPool的成员变量workSequence）得来的
         */
        log.info("start testOneConsumerService");
        testLowLevelOperateService(oneConsumer);
    }

    @Test
    public void testMultiConsumer() throws InterruptedException {
        log.info("start testMultiConsumer");
        testLowLevelOperateService(multiConsumer);
    }

    @Test
    public void testWorkerPoolConsumer() throws InterruptedException {
        log.info("start testWorkerPoolConsumer");
        testLowLevelOperateService(workerPoolConsumer);
    }

    private void testLowLevelOperateService(LowLevelOperateService service) throws InterruptedException {
        for (int i = 0; i < EVENT_COUNT; i++) {
            log.info("publish {}", i);
            service.publish(String.valueOf(i));
        }
        // 异步消费，因此需要延时等待
        Thread.sleep(10000);
    }
}
