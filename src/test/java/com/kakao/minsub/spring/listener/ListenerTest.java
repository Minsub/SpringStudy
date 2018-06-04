package com.kakao.minsub.spring.listener;

import com.kakao.minsub.spring.BaseSpringTest;
import com.kakao.minsub.spring.service.TestService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import static org.junit.Assert.*;

public class ListenerTest extends BaseSpringTest {
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    
    @Autowired
    private TestService testService;
    
    @Test
    public void publish() {
        eventPublisher.publishEvent(new SampleEvent("test message!!!"));
        assertNotNull(testService);
    }
}
