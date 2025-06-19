package com.example.demo.spring;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;

/**
 * ApplicationListener 是一个用于监听 应用事件 的核心接口。
 * 通过实现该接口，开发者可以订阅 Spring 容器生命周期中的关键事件或自定义事件，并在事件发生时执行特定逻辑.
 * 事件完整调用逻辑分为发布事件和监听事件.
 * 1.发布事件,通过实现 ApplicationEvent 接口创建事件对象,通过{@link org.springframework.context.ApplicationEventPublisher#publishEvent(ApplicationEvent)} 发布事件
 * 2.1:监听事件,通过实现 ApplicationListener 接口并指定需要监听的事件对象来创建监听器,事件发布后可以监听到走 onApplicationEvent()逻辑
 * 2.2:监听事件:Spring 4.2+ 支持通过 @EventListener 注解简化事件监听，无需实现 ApplicationListener 接口,并且可以配合 @Order 按指定顺序执行多个监听器
 * Spring 内置事件:
 * ContextRefreshedEvent：应用上下文初始化或刷新完成时触发。
 * ContextStartedEvent：上下文通过 start() 方法启动后触发。
 * ContextStoppedEvent：上下文通过 stop() 方法停止后触发。
 * ContextClosedEvent：上下文关闭时触发。
 * RequestHandledEvent：HTTP 请求处理完成后触发（需 Web 环境）。
 * Spring Boot 应用启动过程中，事件触发顺序如下
 * 1. ApplicationStartingEvent
 * 2. ApplicationEnvironmentPreparedEvent
 * 3. ApplicationContextInitializedEvent
 * 4. ContextRefreshedEvent: 基于Spring 框架的事件,容器刷新完成（Bean 初始化后）.         <-- Spring 容器已刷新
 * 5. ApplicationStartedEvent :上下文通过 start() 方法启动后触发
 * 6. ApplicationReadyEvent: 基于 Spring Boot 框架的事件,应用完全就绪（包括内嵌服务器启动）       <-- 应用完全就绪
 */
public class ApplicationListenerTest implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("ApplicationListener 监听到 ContextRefreshedEvent");
    }

    @Order(1)
    @EventListener
    public void handleContextRefresh1(ContextRefreshedEvent event) {
        System.out.println("@EventListener11111 监听到 ContextRefreshedEvent");
    }

    @Order(2)
    @EventListener(ContextRefreshedEvent.class)
    public void handleContextRefresh2(ContextRefreshedEvent event) {
        System.out.println("@EventListener22222 监听到 ContextRefreshedEvent");
    }

    /*
    1.指定@Async线程池 异步执行事件
    @Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {
    @Override
    public Executor getAsyncExecutor() {
        return Executors.newFixedThreadPool(3);
    }
}

@Component
public class AsyncEventListener {
    @EventListener
    @Async
    public void asyncHandleEvent(CustomEvent event) {
        System.out.println("异步处理事件: " + Thread.currentThread().getName());
    }
}
     */
}
