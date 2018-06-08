package com.kakao.minsub.spring.config;

import com.kakao.minsub.spring.BaseSpringTest;
import com.kakao.minsub.spring.model.Profile;
import com.rabbitmq.client.*;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

//public class RabbitMqTest extends BaseSpringTest {
public class RabbitMqTest {
    private final Logger logger = LoggerFactory.getLogger(RabbitMqTest.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    private final static String QUEUE_NAME = "queue-test";
    
    @Test
    public void producerTest() throws Exception {
        Assert.assertNotNull(rabbitTemplate);
    
        IntStream.range(1, 10).forEach(value -> {
            try {
                Profile p = new Profile();
                p.setId(1);
                p.setName("test name");
                rabbitTemplate.convertAndSend(p);
                logger.info("SENT MESSAGE: {}", value);
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                logger.error("ERROR", e);
            }
        });
    }
    
    @Test
    public void rawProducerTest() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("jiminsub.iptime.org");
        factory.setUsername("rabbitmq");
        factory.setPassword("wlalstjq1");
        Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();
    
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        
        IntStream.range(1, 10).forEach(value -> {
            try {
                String message = "Hello World - " + value;
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                logger.info("SENT: {}", message );
            } catch (Exception e){
                logger.error("ERROR SEND MESSAGE", e);
            }
        });
    
        channel.close();
        connection.close();
    }
    
    @Test
    public void rawConsumerTest() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("jiminsub.iptime.org");
        factory.setUsername("rabbitmq");
        factory.setPassword("wlalstjq1");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                logger.info("[{}] Consumer message: {}" , Thread.currentThread().getName() ,message);
            }
        };
    
        channel.basicConsume(QUEUE_NAME, true, consumer);
        
        TimeUnit.SECONDS.sleep(60);
        
        channel.close();
        connection.close();
    }
    
    @Test
    public void rawConsumerBroadcastTest() throws Exception {
        Executor executor = Executors.newFixedThreadPool(3);
        Runnable job = new Runnable() {
            @Override
            public void run() {
                try {
                    RabbitMqTest test = new RabbitMqTest();
                    test.rawConsumerTest();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        
        
        executor.execute(job);
        executor.execute(job);
    
    
        TimeUnit.SECONDS.sleep(120);
    }
}
