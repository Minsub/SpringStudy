package com.kakao.minsub.spring.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.minsub.spring.model.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

@Component
@ConditionalOnBean(name = "rabbitMQObjectMapper")
public class RabbitMQConsumer {
    private final Logger logger = LoggerFactory.getLogger(RabbitMQConsumer.class);
    private final static String QUEUE_NAME = "queue-test";
    
    @Resource(name="rabbitMQObjectMapper")
    private ObjectMapper objectMapper;
    
    @RabbitListener(queues = {RabbitMQConsumer.QUEUE_NAME})
    public void handleMessage(Message message) {
        logger.info("rabbitMQ consumer. message: {}", message);
        
        try {
            String profileMessage = new String(message.getBody());
            Profile profile = objectMapper.readValue(profileMessage, Profile.class);
            logger.info("rabbitMQ consumer. profile: {}", profile);
        } catch (Exception e) {
            logger.error("handle message Error ", e);
        }
    }
}
