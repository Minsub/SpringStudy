package com.kakao.minsub.spring.consumer;

import com.google.common.collect.Lists;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Properties;

public class KafkaProfileConsumer implements Runnable {
    Logger logger = LoggerFactory.getLogger(KafkaProfileConsumer.class);
    
    private String topic;
    private Properties kafkaConsumerProp;
    
    public KafkaProfileConsumer(String topic, Properties kafkaConsumerProp) {
        this.topic = topic;
        this.kafkaConsumerProp = kafkaConsumerProp;
    }
    
    @Override
    public void run() {
        logger.info("start profile consumer");
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(kafkaConsumerProp);
        kafkaConsumer.subscribe(Lists.newArrayList(topic));
    
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(1000);
//            if (records.isEmpty()) {
//                logger.info("Empty records: {}", topic);
//                continue;
//            }
            for (ConsumerRecord<String, String> record : records) {
                logger.info("Consumer: topic = {}, offset = {}, key = {}, value = {}", topic, record.offset(), record.key(), record.value());
            }
            kafkaConsumer.commitAsync();
        }
    }
}
