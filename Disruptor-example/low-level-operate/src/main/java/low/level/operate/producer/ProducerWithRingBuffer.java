package low.level.operate.producer;

import com.lmax.disruptor.RingBuffer;
import common.event.StringEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Pecker
 * @since 2025/6/10 15:57
 */
@Slf4j
@Component
public class ProducerWithRingBuffer {

    @Autowired
    RingBuffer<StringEvent> ringBuffer;


    public void onData(String content) {
        // ringBuffer是个队列，其next方法返回的是下最后一条记录之后的位置，这是个可用位置
        long sequence = ringBuffer.next();

        try {
            // sequence位置取出的事件是空事件
            StringEvent event = ringBuffer.get(sequence);
            // 空事件添加业务信息
            event.setValue(content);
        } finally {
            // 发布
            log.info("disruptor 发布事件,RingBuffer next Sequence = {}", sequence);
            ringBuffer.publish(sequence);
        }
    }
}
