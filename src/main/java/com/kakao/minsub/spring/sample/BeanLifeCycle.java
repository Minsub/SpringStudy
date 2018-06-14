package com.kakao.minsub.spring.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;

@Configuration
@PropertySource("classpath:common-${spring.profiles.active}.properties")
@ConfigurationProperties(prefix="cache.redis")
//@Component
public class BeanLifeCycle implements InitializingBean, DisposableBean {
    private final Logger logger = LoggerFactory.getLogger(BeanLifeCycle.class);
    
    String host;
    
    public String getHost() { return host; }
    public void setHost(String host) { this.host = host; }
    
//    @Autowired
    private ApplicationLifeCycle applicationLifeCycle;
    
    @PostConstruct
    public void postConstruct() {
        logger.info("[GRAY_TEST] BeanLifeCycle: postConstruct. props: {}, autowired bean: {}", host, applicationLifeCycle);
    }
    
    @Override
    public void destroy() throws Exception {
        logger.info("[GRAY_TEST] BeanLifeCycle: destroy. props: {}, autowired bean: {}", host, applicationLifeCycle);
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("[GRAY_TEST] BeanLifeCycle: afterPropertiesSet. props: {}, autowired bean: {}", host, applicationLifeCycle);
    }
    
    public void print() {
        logger.info("[GRAY_TEST] BeanLifeCycle: PRINT!!");
    }
}
