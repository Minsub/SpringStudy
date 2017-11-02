package com.kakao.minsub.spring.listener;

import org.springframework.context.ApplicationEvent;

public class SampleEvent22 extends ApplicationEvent {
    private String message;
    
    public SampleEvent22(String message) {
        super(message);
        this.message = message;
    }
    
    public String getMessage() {
        return this.message;
    }
}
