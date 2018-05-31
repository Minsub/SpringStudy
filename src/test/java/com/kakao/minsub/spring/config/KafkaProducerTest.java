package com.kakao.minsub.spring.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.kakao.minsub.spring.BaseSpringTest;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Map;

public class KafkaProducerTest extends BaseSpringTest {
    
    @Autowired
    private Producer<String, String> kafkaProducer;
    
    @Autowired
    private KafkaConfig kafkaConfig;
    
    @Test
    public void producerTest() throws Exception {
        Assert.assertNotNull(kafkaProducer);
        final String topic = "test";
//        final ProducerRecord<String, String> record = new ProducerRecord<>(topic, 0, "testKey", valueGenerate());
        final ProducerRecord<String, String> record = new ProducerRecord<>(topic, "Hello World: "+LocalDateTime.now().toString());
        kafkaProducer.send(record);
        kafkaProducer.close();
    }
    
    @Test
    public void consumerTest() throws Exception {
        kafkaConfig.startConsumer();
    }
    
    private String valueGenerate() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> input = Maps.newHashMap();
        input.put("value", "Hello!!");
        input.put("timestamp", LocalDateTime.now().toString());
        return mapper.writeValueAsString(input);
    }
}
