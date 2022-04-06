package com.example.demo.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @author Pecker
 * @Description 事件发布
 * @since 2022-03-15
 */
@Component
public class ExampleEventPublisher {
    @Autowired
    ApplicationEventPublisher applicationEventPublisher;


    /**
     * 封装事件发布入口,最终还是要调用ApplicationEventPublisher去发布事件
     *
     * @param eventId eventId
     */
    public void publishEvent(String eventId) {
        ExampleEvent event = new ExampleEvent(this, eventId);
        applicationEventPublisher.publishEvent(event);
    }
}
