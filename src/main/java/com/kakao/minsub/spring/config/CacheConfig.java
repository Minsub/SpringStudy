package com.kakao.minsub.spring.config;

import com.google.common.collect.Maps;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.AbstractCachingConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import java.time.Duration;

@Configuration
@EnableCaching
@PropertySource("classpath:common-${spring.profiles.active}.properties")
@ConfigurationProperties(prefix="cache.redis")
@Data
public class CacheConfig extends AbstractCachingConfiguration implements InitializingBean {
    private String host;
    private int port;
    private String password;
    private int defaultExpiration = 0;
    private int timeout = 0;
    private int database = 0;
    
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        LettuceConnectionFactory factory = new LettuceConnectionFactory(host,port);
        factory.setDatabase(database);
        factory.setPassword(password);
        factory.setTimeout(timeout);
//		factory.setShareNativeConnection(false);
        factory.afterPropertiesSet();
        factory.initConnection();
        return factory;
    }
    
    @Bean
    public RedisTemplate<String, ?> redisTemplate() {
        RedisTemplate<String, ?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }
    
    @Bean
    public CacheManager cacheManager() {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
            .computePrefixWith(key -> String.format("spring.cache.%s::", key))
            .entryTtl(defaultExpiration > 0 ? Duration.ofSeconds(defaultExpiration) : Duration.ZERO)
        ;
        return RedisCacheManager.builder(redisConnectionFactory())
                .cacheDefaults(config)
                .transactionAware()
                .build();
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.hasLength(host, "host is null");
        Assert.isTrue( port > 0 , "port should be positive.");
        Assert.hasLength(password, "host is null");
    }
}
