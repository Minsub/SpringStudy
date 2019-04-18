package com.kakao.minsub.spring.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@Configuration
@PropertySource("classpath:common-${spring.profiles.active}.properties")
@Profile("development")
@Order(0)
@Slf4j
public class EmbeddedRedisConfig {
  
    @Value("${cache.redis.port}")
    private int redisPort;
    
    private RedisServer redisServer;
    
    @PostConstruct
    public void redisServer() throws IOException {
        this.redisServer = new RedisServer(this.redisPort);
        try {
            this.redisServer.start();
        } catch (Exception e) {
            log.warn("already embedded redis is staring maybe...", e);
        }
        
    }
    
    @PreDestroy
    public void stopRedis() {
        if (this.redisServer != null) {
            this.redisServer.stop();
        }
    }
}
