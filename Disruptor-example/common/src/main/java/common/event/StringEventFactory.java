package common.event;

import com.lmax.disruptor.EventFactory;

/**
 * @author Pecker
 * @since 2025/6/10 16:25
 */
public class StringEventFactory implements EventFactory<StringEvent> {
    @Override
    public StringEvent newInstance() {
        return new StringEvent();
    }
}
