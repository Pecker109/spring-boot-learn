package low.level.operate.service.impl;

import lombok.extern.slf4j.Slf4j;
import low.level.operate.producer.ProducerWithRingBuffer;
import low.level.operate.service.LowLevelOperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Pecker
 * @since 2025/6/11 13:32
 */
@Service("oneConsumer")
@Slf4j
public class OneConsumerServiceImpl implements LowLevelOperateService {

    @Autowired
    ProducerWithRingBuffer stringEventProducer;

    @Override
    public void publish(String value) {
        stringEventProducer.onData(value);
    }
}
