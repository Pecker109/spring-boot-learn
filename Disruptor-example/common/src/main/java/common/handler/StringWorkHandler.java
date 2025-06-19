package common.handler;

import com.lmax.disruptor.WorkHandler;
import common.event.StringEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Pecker
 * @since 2025/6/19 13:56
 */
@Slf4j
public class StringWorkHandler implements WorkHandler<StringEvent> {
    @Override
    public void onEvent(StringEvent event) throws Exception {
        log.info("work handler event : {}", event);

        // 这里延时100ms，模拟消费事件的逻辑的耗时
        Thread.sleep(100);

    }
}
