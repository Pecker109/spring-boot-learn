package com.example.demo.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author Pecker
 * @Description 监听事件, 对事件处理
 * @since 2022-03-15
 */
@Slf4j
@Component
public class ExampleEventHandle {

    @EventListener
    public void actionEvent(ExampleEvent event) {
        // 监听到事件后进行处理
        log.info(event.getEventId());
    }
}
