package basic.event.service.service;

import basic.event.service.producer.StringEventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Pecker
 * @since 2025/6/10 16:33
 */
@Component
public class BasicEventService {

    @Autowired
    StringEventProducer eventProducer;

    public void publish(String value) {
        eventProducer.onData(value);
    }
}
