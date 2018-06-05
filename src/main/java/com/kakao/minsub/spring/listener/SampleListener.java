package com.kakao.minsub.spring.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.minsub.spring.model.Profile;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.PartitionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.Future;

@Component
public class SampleListener {
    private Logger logger = LoggerFactory.getLogger(SampleListener.class);
    
    @Autowired
    private Producer<String, String> kafkaProducer;
    
    @Value("${kafka.topic}")
    private String topic;
    
    @Resource(name = "kafkaJacksonObjectMapper")
    private ObjectMapper objectMapper;
    
    @EventListener
    protected Future<Void> handleSampleEvent(final SampleEvent event) {
        logger.info("handleEvent: {}", event.getMessage());
        return null;
    }
    
    @EventListener
    protected Future<Void> handleKafkaEvent(final KafkaEvent<Profile> profileEvent) {
        Profile profile = profileEvent.getTarget();
        logger.info("handleEvent: {}", profile);
        
        try {
            List<PartitionInfo> partitionInfos = kafkaProducer.partitionsFor(topic);
            final String key = String.valueOf(profile.getId());
            final int partition = profile.getId() % partitionInfos.size();
            final String serialized = objectMapper.writeValueAsString(profile);
            final ProducerRecord<String, String> record = new ProducerRecord<>(topic, partition, key, serialized);
            kafkaProducer.send(record);
            logger.info("sent success Kafka<Profile>: (topic: {}, partition: {}, key: {}, msg: {}", topic, partition, key, serialized);
        } catch (Exception e) {
            logger.error("error kafkaEvent<Profile>: {}", e.getMessage());
        }
        return null;
    }
    
    
    
    @PreDestroy
    public void destroy() throws Exception {
        logger.info("Destroying SampleListener");
        kafkaProducer.close();
    }
}
