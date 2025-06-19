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
@Service("workerPoolConsumer")
@Slf4j
public class WorkerPoolServiceImpl implements LowLevelOperateService {

    @Autowired
    ProducerWithRingBuffer stringEventProducer;

    @Autowired
    RingBuffer<StringEvent> workerPoolRingBuffer;

    @Override
    public void publish(String value) {
        stringEventProducer.onData(workerPoolRingBuffer, value);
    }
}
