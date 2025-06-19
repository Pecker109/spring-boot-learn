package consumer.model;

import com.lmax.disruptor.dsl.Disruptor;
import consumer.model.config.*;
import consumer.model.event.OrderEvent;
import consumer.model.producer.OrderEventProducer;
import consumer.model.service.ConsumeModeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Pecker
 * @since 2025/6/19 15:41
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ConsumeModeServiceTest {

    @Autowired
    OrderEventProducer orderEventProducer1;

    @Autowired
    OrderEventProducer orderEventProducer2;

    @Autowired
    ConsumeModeService consumeModeService;


    @Autowired
    @Qualifier("independentModeDisruptor")
    IndependentModeDisruptor independentModeDisruptor;

    @Autowired
    @Qualifier("shareModeDisruptor")
    ShareModeDisruptor shareModeDisruptor;

    @Autowired
    @Qualifier("shareAndIndependentModeDisruptor")
    ShareAndIndependentModeDisruptor shareAndIndependentModeDisruptor;

    @Autowired
    MultiProducerModeDisruptor multiProducerModeDisruptor;

    @Autowired
    TestDisruptor5 testDisruptor5;

    @Autowired
    TestDisruptor6 testDisruptor6;

    @Autowired
    TestDisruptor7 testDisruptor7;

    @Autowired
    TestDisruptor8 testDisruptor8;

    @Autowired
    TestDisruptor9 testDisruptor9;

    @Autowired
    TestDisruptor10 testDisruptor10;

    /**
     * 测试时生产的消息数量
     */
    private static final int EVENT_COUNT = 100;


    @Test
    public void testIndependentMode() throws InterruptedException {
        // 发布事件个数 = EVENT_COUNT = 100
        // 处理事件个数=  发布事件个数 * handleEventsWith 个数 = 100 * 2 = 200
        testConsumeModeService(independentModeDisruptor.getDisruptor(), EVENT_COUNT);
    }

    @Test
    public void testShareMode() throws InterruptedException {
        // 发布事件个数 = EVENT_COUNT = 100
        // 处理事件个数=  发布事件个数  = 100 (Disruptor#handleEventsWithWorkerPool()共同消费)
        testConsumeModeService(shareModeDisruptor.getDisruptor(), EVENT_COUNT);
    }

    @Test
    public void testShareAndIndependentModeService() throws InterruptedException {
        // 发布事件个数 = EVENT_COUNT = 100
        // 处理事件个数=  (发布事件个数 * handleEventsWith 个数)+(发布事件个数 handleEventsWithWorkerPool) = 100*1+100=200
        testConsumeModeService(shareAndIndependentModeDisruptor.getDisruptor(), EVENT_COUNT);
    }

    private void testConsumeModeService(Disruptor<OrderEvent> disruptor, int eventCount) throws InterruptedException {
        for (int i = 0; i < eventCount; i++) {
            log.info("publish {}", i);
            consumeModeService.publish(orderEventProducer1, disruptor.getRingBuffer(), String.valueOf(i));
        }
        Thread.sleep(5000);
    }

    @Test
    public void testMultiProducerService() throws InterruptedException {
        // 发布事件个数 = 100 * 2
        // 处理事件个数=  发布事件个数 * handleEventsWith 个数 = 200 * 2 = 400
        for (int i = 0; i < EVENT_COUNT; i++) {
            log.info("publish {}", i);
            orderEventProducer1.onData(multiProducerModeDisruptor.getDisruptor().getRingBuffer(), String.valueOf(i));
            orderEventProducer2.onData(multiProducerModeDisruptor.getDisruptor().getRingBuffer(), String.valueOf(i));
        }
        Thread.sleep(5000);
    }

    @Test
    public void testScene5() throws InterruptedException {
        // 三个独立消费者，一共消费300个事件
        testConsumeModeService(testDisruptor5.getDisruptor(), EVENT_COUNT);
    }

    @Test
    public void testScene6() throws InterruptedException {
        // 4个独立消费者，一共消费400个事件
        testConsumeModeService(testDisruptor6.getDisruptor(), EVENT_COUNT);
    }

    @Test
    public void testScene7() throws InterruptedException {
        // 5个独立消费者，一共消费500个事件
        testConsumeModeService(testDisruptor7.getDisruptor(), EVENT_COUNT);
    }

    @Test
    public void testScene8() throws InterruptedException {
        // c1,c2 共同消费 100 个;c3,c4 共同消费 100 个;c5 消费 100 个.一共消费300个事件
        testConsumeModeService(testDisruptor8.getDisruptor(), EVENT_COUNT);
    }

    @Test
    public void testScene9() throws InterruptedException {
        // c1,c2 共同消费 100 个;c3,c4 各消费 100 个;c5 消费 100 个.一共消费400个事件
        testConsumeModeService(testDisruptor9.getDisruptor(), EVENT_COUNT);
    }
   @Test
    public void testScene10() throws InterruptedException {
        // c1,c2 各消费 100 个;c3,c4 共同消费 100 个;c5 消费 100 个.一共消费400个事件
        testConsumeModeService(testDisruptor10.getDisruptor(), EVENT_COUNT);
    }

}
