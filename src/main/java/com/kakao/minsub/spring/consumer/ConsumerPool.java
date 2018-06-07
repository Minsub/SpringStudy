package com.kakao.minsub.spring.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ConsumerPool {
    Logger logger = LoggerFactory.getLogger(ConsumerPool.class);
    
    @Resource(name="kafkaConsumerProp")
    private Properties kafkaConsumerProp;

    @Value("${kafka.topic}")
    private String topic;
    
    @Value("${kafka.enable}")
    private boolean enable;
    
    private ExecutorService executorService;

    @PostConstruct
    public void startConsumer() throws Exception {
        if (enable) {
            logger.info("startConsumer");
            this.executorService = Executors.newFixedThreadPool(3);
            executorService.submit(new ProfileConsumer(topic, kafkaConsumerProp));
        }
    }
}
