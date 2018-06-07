package com.kakao.minsub.spring.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.kakao.minsub.spring.BaseSpringTest;
import com.kakao.minsub.spring.service.TestService;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class AsyncTaskExecutorTest extends BaseSpringTest {
    private final Logger logger = LoggerFactory.getLogger(AsyncTaskExecutorTest.class);

    @Autowired
    private TestService testService;
    
    @Test
    public void test() throws Exception {
        Assert.assertNotNull(testService);
//        testService.print(7);
    
//        TimeUnit.SECONDS.sleep(3);
    
        testService.asyncPrint(7);
    }
    
}
