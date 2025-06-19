package common.handler;

import com.lmax.disruptor.EventHandler;
import common.event.StringEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Pecker
 * @since 2025/6/10 15:57
 */
@Slf4j
public class StringEventHandler implements EventHandler<StringEvent> {

    /**
     * 统计消息总数
     */
    private final AtomicLong eventCount = new AtomicLong();

    @Override
    public void onEvent(StringEvent event, long sequence, boolean endOfBatch) throws Exception {
        log.info("sequence [{}], endOfBatch [{}], event : {}", sequence, endOfBatch, event);

        // 这里延时100ms，模拟消费事件的逻辑的耗时
        Thread.sleep(100);

        long count = eventCount.incrementAndGet();
        log.info("receive [{}] event", count);
    }
}
