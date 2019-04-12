package com.kakao.minsub.spring.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@ConditionalOnBean(name = "kafkaConsumerProp")
public class KafkaConsumerPool {
    Logger logger = LoggerFactory.getLogger(KafkaConsumerPool.class);
    
    @Resource(name="kafkaConsumerProp")
    private Properties kafkaConsumerProp;

    @Value("${kafka.topic}")
    private String topic;
    
    @Value("${kafka.enabled}")
    private boolean enabled;
    
    private ExecutorService executorService;

    @PostConstruct
    public void startConsumer() throws Exception {
        if (enabled) {
            logger.info("startConsumer");
            this.executorService = Executors.newFixedThreadPool(3);
            executorService.submit(new KafkaProfileConsumer(topic, kafkaConsumerProp));
        }
    }
}
