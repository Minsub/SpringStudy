package com.kakao.minsub.spring.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.kakao.minsub.spring.config.jersey.filter.ManagerFilter;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import java.util.List;
import java.util.Map;
import java.util.Properties;

@Configuration
@PropertySource("classpath:common-${spring.profiles.active}.properties")
public class KafkaConfig {
    private final Logger logger = LoggerFactory.getLogger(KafkaConfig.class);
    
    @Value("${kafka.bootstrap.servers}")
    private String bootstrapServers;
    
    @Value("${kafka.consumer.group}")
    private String consumerGroup;
    
    @Value("${kafka.topic}")
    private String topic;
    
    @Value("${kafka.worker.count}")
    private int workerCount;
    
    @Bean
    @Primary
    public Producer<String, String> kafkaProducer() {
        final Properties props = new Properties();

        props.put(ProducerConfig.METADATA_FETCH_TIMEOUT_CONFIG, "1000");
        props.put(ProducerConfig.BLOCK_ON_BUFFER_FULL_CONFIG, false);

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "gzip");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        return producer;
    }
    
    
    public void startConsumer() throws Exception {
        final Properties props = new Properties();
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG, consumerGroup);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
    
//        ObjectMapper objectMapper = new ObjectMapper();
        
        ConsumerConfig consumerConfig = new ConsumerConfig(props);
        ConsumerConnector consumerConnector = Consumer.createJavaConsumerConnector(consumerConfig);
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumerConnector.createMessageStreams(ImmutableMap.of(topic, workerCount));
        List<KafkaStream<byte[], byte[]>> kafkaStreams = consumerMap.get(topic);
        for (KafkaStream<byte[], byte[]> kafkaStream: kafkaStreams) {
            ConsumerIterator<byte[], byte[]> consumerIterator = kafkaStream.iterator();
            while (consumerIterator.hasNext()) {
                try {
                    MessageAndMetadata<byte[], byte[]> consumeData = consumerIterator.next();
                    String message = new String(consumeData.message());
                    logger.info("consumer : {}", message);
                } catch (Exception e) {
                    logger.error("system signal error : {}",e );
                }
            }
        }
    }
}
