package com.kakao.minsub.spring.config;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.kakao.minsub.spring.config.serialize.ProfileSerializer;
import com.kakao.minsub.spring.model.Profile;
import lombok.Data;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:common-${spring.profiles.active}.properties")
@ConfigurationProperties(prefix = "queue.rabbitmq")
@Data
public class RabbitMQConfig {
    String host;
    String username;
    String password;
    int port = 5672;
    String queuename;
    
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setRoutingKey(queuename);
        rabbitTemplate.setMessageConverter(getConverter());
        return rabbitTemplate;
    }
    
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(username);
        factory.setPassword(password);
        return factory;
    }
    
    @Bean
    public ProfileSerializer profileSerializer() {
        return new ProfileSerializer();
    }
    
    private Jackson2JsonMessageConverter getConverter() {
        Jackson2JsonMessageConverter jsonMessageConverter = new Jackson2JsonMessageConverter(rabbitMQObjectMapper());
        return jsonMessageConverter;
    }
    
    @Bean
    public ObjectMapper rabbitMQObjectMapper() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Profile.class, profileSerializer());
        
        return new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE)
                .registerModule(simpleModule);
    }
    
}
