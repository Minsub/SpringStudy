package com.kakao.minsub.spring.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.kakao.minsub.spring.BaseSpringTest;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class KafkaProducerTest extends BaseSpringTest {
    private final Logger logger = LoggerFactory.getLogger(KafkaProducerTest.class);

    @Autowired
    private Producer<String, String> kafkaProducer;
    
    @Autowired
    private KafkaConfig kafkaConfig;

    @Autowired
    private KafkaConsumer<String, String> kafkaConsumer;

    private final static String TEST_TOPIC = "test";
    
    @Test
    public void producerTest() throws Exception {
        Assert.assertNotNull(kafkaProducer);
        final String topic = TEST_TOPIC;
//        final ProducerRecord<String, String> record = new ProducerRecord<>(topic, 0, "testKey", valueGenerate());
        final ProducerRecord<String, String> record = new ProducerRecord<>(topic, "Hello World: "+LocalDateTime.now().toString());
        kafkaProducer.send(record);
        kafkaProducer.close();
    }
    
    @Test
    public void consumerTest() throws Exception {
        Assert.assertNotNull(kafkaConsumer);
        kafkaConsumer.subscribe(Lists.newArrayList(TEST_TOPIC));
        int maxCount = 10;
        int cnt = 0;
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(1000);
            if (records.isEmpty()) {
                logger.info("Empty records");
//                TimeUnit.SECONDS.sleep(2);
                continue;
            }
            for (ConsumerRecord<String, String> record : records) {
                logger.info("offset = {}, key = {}, value = {}", record.offset(), record.key(), record.value());
            }
            kafkaConsumer.commitAsync();
            cnt += records.count();
            if (cnt > maxCount) {
                break;
            }
        }
        kafkaConsumer.close();
        logger.info("FINISH");
    }

    private String valueGenerate() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> input = Maps.newHashMap();
        input.put("value", "Hello!!");
        input.put("timestamp", LocalDateTime.now().toString());
        return mapper.writeValueAsString(input);
    }
}
