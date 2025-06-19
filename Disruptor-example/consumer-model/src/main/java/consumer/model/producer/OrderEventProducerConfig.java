package consumer.model.producer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Pecker
 * @since 2025/6/19 20:33
 */
@Configuration
public class OrderEventProducerConfig {

    @Bean(value = "orderEventProducer1")
    public OrderEventProducer orderEventProducer1() {
        return new OrderEventProducer();
    }

    @Bean(value = "orderEventProducer2")
    public OrderEventProducer orderEventProducer2() {
        return new OrderEventProducer();
    }
}
