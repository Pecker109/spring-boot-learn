package com.example.demo.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2022-03-15
 */
public class ExampleEvent extends ApplicationEvent {

    private String eventId;

    public ExampleEvent(Object source, String eventId) {
        super(source);
        this.eventId = eventId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}
