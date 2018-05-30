package com.kakao.minsub.spring.redis;

import com.kakao.minsub.spring.BaseSpringTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class redisTest extends BaseSpringTest {
    
    @Autowired
    private RedisTemplate<String, ?> redisTemplate;
    
    @Test
    public void redisTemplateCreation() {
        Assert.assertNotNull(redisTemplate);
    
        redisTemplate.boundHashOps("TEST").put("testKey", 123);
    
        int result = (int)redisTemplate.boundHashOps("TEST").get("testKey");
        
        Assert.assertEquals(123, result);
    }
}
