package com.kakao.minsub.spring.config.undertow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationClosedEventListener {
    private final Logger logger = LoggerFactory.getLogger(ApplicationClosedEventListener.class);
    
    @Autowired
    private UndertowShutdownHandlerWrapper undertowShutdownHandlerWrapper;
    
    @EventListener
    public void processContextClosedEvent(ContextClosedEvent event) {
        try {
            undertowShutdownHandlerWrapper.getGracefulShutdownHandler().shutdown();
            undertowShutdownHandlerWrapper.getGracefulShutdownHandler().awaitShutdown(5000);
        } catch (InterruptedException e) {
            logger.error("undertow graceful shutdown handler error", e);
        }
    }
}
