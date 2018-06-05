package com.kakao.minsub.spring.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.kakao.minsub.spring.BaseSpringTest;
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

public class KafkaProducerTest extends BaseSpringTest {
    private final Logger logger = LoggerFactory.getLogger(KafkaProducerTest.class);

    @Autowired
    private Producer<String, String> kafkaProducer;
    
    @Autowired
    private KafkaConfig kafkaConfig;
    
    @Resource(name="kafkaConsumerProp")
    private Properties kafkaConsumerProp;

    private final static String TEST_TOPIC = "test";
    
    @Test
    public void producerTest() throws Exception {
        Assert.assertNotNull(kafkaProducer);
        final String topic = TEST_TOPIC;
        final ProducerRecord<String, String> record = new ProducerRecord<>(topic, 0, "testKey", valueGenerate());
//        final ProducerRecord<String, String> record = new ProducerRecord<>(topic, "Hello World: "+LocalDateTime.now().toString());
        kafkaProducer.send(record);
        kafkaProducer.close();
    }
    
    @Test
    public void consumerTest() throws Exception {
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(kafkaConsumerProp);
        
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
    
    @Test
    public void multoBrokerProducerTest() throws Exception {
        final Properties props = new Properties();
    
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "jiminsub.iptime.org:9092,jiminsub.iptime.org:9093,jiminsub.iptime.org:9094");
        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "gzip");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
    
        kafkaProducer.send(new ProducerRecord<>("my-replicated-topic", 0, "testKey-0", valueGenerate()));
        kafkaProducer.send(new ProducerRecord<>("my-replicated-topic", 0, "testKey-1", valueGenerate()));
        kafkaProducer.send(new ProducerRecord<>("my-replicated-topic", 0, "testKey-2", valueGenerate()));
        kafkaProducer.send(new ProducerRecord<>("my-replicated-topic", 0, "testKey-0", valueGenerate()));
        kafkaProducer.close();
    }

    @Test
    public void multiBrokerConsumerTest() {
        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "jiminsub.iptime.org:9092,jiminsub.iptime.org:9093,jiminsub.iptime.org:9094");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "testGroup");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
    
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(props);
        kafkaConsumer.subscribe(Lists.newArrayList("my-replicated-topic"));
    
        int maxCount = 5;
        int cnt = 0;
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(1000);
            if (records.isEmpty()) {
                logger.info("Empty records");
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
