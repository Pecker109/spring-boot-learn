package low.level.operate.service.impl;

import com.lmax.disruptor.RingBuffer;
import common.event.StringEvent;
import lombok.extern.slf4j.Slf4j;
import low.level.operate.producer.ProducerWithRingBuffer;
import low.level.operate.service.LowLevelOperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Pecker
 * @since 2025/6/11 13:32
 */
@Service("multiConsumer")
@Slf4j
public class MultiConsumerServiceImpl implements LowLevelOperateService {

    @Autowired
    ProducerWithRingBuffer stringEventProducer;

    @Autowired
    RingBuffer<StringEvent> multiConsumerRingBuffer;

    @Override
    public void publish(String value) {
        stringEventProducer.onData(multiConsumerRingBuffer, value);
    }
}
