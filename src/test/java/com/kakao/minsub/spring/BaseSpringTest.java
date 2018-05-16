package com.kakao.minsub.spring;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class BaseSpringTest {
    @BeforeClass
    public static void resolveActiveProfiles() {
        System.setProperty("spring.profiles.active", "development");
    }
    
}
