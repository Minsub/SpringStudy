package com.kakao.minsub.spring.config;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.AbstractCachingConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

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
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(host);
        factory.setPort(port);
        factory.setPassword(password);
        factory.setUsePool(true);
        factory.setTimeout(timeout);
        factory.setDatabase(database);
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
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate());
        cacheManager.setCachePrefix(cacheName -> String.format("spring.cache.%s",cacheName).getBytes());
        if (defaultExpiration > 0) {
            cacheManager.setUsePrefix(true);
            cacheManager.setDefaultExpiration(defaultExpiration);
        }
        return cacheManager;
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.hasLength(host, "host is null");
        Assert.isTrue( port > 0 , "port should be positive.");
        Assert.hasLength(password, "host is null");
    }
}
