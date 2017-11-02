package com.kakao.minsub.spring.listener;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ListenerTest {
    
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    
    @Test
    public void publish() {
        eventPublisher.publishEvent(new SampleEvent("test message!!!"));
    
        eventPublisher.publishEvent(new SampleEvent22("test message22222!!!"));
    }
}
