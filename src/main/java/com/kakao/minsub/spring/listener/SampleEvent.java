package com.kakao.minsub.spring.listener;

import org.springframework.context.ApplicationEvent;

public class SampleEvent extends ApplicationEvent {
    private String message;
    
    public SampleEvent(String message) {
        super(message);
        this.message = message;
    }
    
    public String getMessage() {
        return this.message;
    }
}
