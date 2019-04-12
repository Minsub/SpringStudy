package com.kakao.minsub.spring.listener;

import com.kakao.minsub.spring.client.KafkaProducerClient;
import com.kakao.minsub.spring.model.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Component
@ConditionalOnBean({ KafkaProducerClient.class, RabbitTemplate.class })
public class KafkaListener {
    private Logger logger = LoggerFactory.getLogger(KafkaListener.class);
    
    @Autowired
    private KafkaProducerClient kafkaProducerClient;
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @EventListener
    protected Future<Void> handleSampleEvent(final SampleEvent event) {
        logger.info("handleEvent: {}", event.getMessage());
        return null;
    }
    
    @EventListener
    protected Future<Void> handleKafkaEvent(final KafkaEvent<Profile> profileEvent) {
        Profile profile = profileEvent.getTarget();
        logger.info("handleEvent: {}", profile);
    
        kafkaProducerClient.sendPorifle(profile);
        rabbitTemplate.convertAndSend(profile);
        
        return null;
    }
    
}
