package com.kakao.minsub.spring.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.minsub.spring.model.Profile;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.PartitionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Future;

public class KafkaProducerClient {
    private final Logger logger = LoggerFactory.getLogger(KafkaProducerClient.class);
    
    private Producer<String, String> kafkaProducer;
    private ObjectMapper objectMapper;
    private String topic;
    
    public KafkaProducerClient(Producer<String, String> kafkaProducer, ObjectMapper objectMapper, String topic) {
        this.kafkaProducer = kafkaProducer;
        this.objectMapper = objectMapper;
        this.topic = topic;
    }
    
    
    public Future<RecordMetadata> sendPorifle(Profile profile) {
        if (kafkaProducer == null) {
            return null;
        }
        try {
            List<PartitionInfo> partitionInfos = kafkaProducer.partitionsFor(topic);
            final String key = String.valueOf(profile.getId());
            final int partition = profile.getId() % partitionInfos.size();
            final String serialized = objectMapper.writeValueAsString(profile);
            final ProducerRecord<String, String> record = new ProducerRecord<>(topic, partition, key, serialized);
            Future<RecordMetadata> future = kafkaProducer.send(record);
            logger.info("sent success Kafka<Profile>: (topic: {}, partition: {}, key: {}, msg: {}", topic, partition, key, serialized);
            return future;
        } catch (Exception e) {
            logger.error("error kafkaEvent<Profile>: {}", e.getMessage());
        }
        return null;
    }
}
