package com.kakao.minsub.spring.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Component
public class SampleListener {
    
    @EventListener
    protected Future<Void> handleEvent(final SampleEvent event) {
        System.err.println("handleEvent: " + event.getMessage());
        return null;
    }
    
    @EventListener
    protected Future<Void> handleEvent22(final SampleEvent22 event) {
        System.err.println("handleEvent22: " + event.getMessage());
        return null;
    }
}
