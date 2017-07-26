package com.kakao.minsub.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Properties;

@Configuration
@PropertySource("classpath:test_config.properties")
public class TestConfig {

    @Value("${test.var.name}")
    private String name;

    @Value("${test.var.age}")
    private String age;

    @Bean(name="testProps")
    @ConfigurationProperties(prefix = "test.props")
    public Properties getProperties() {
        Properties props = new Properties();
        props.setProperty("name", name);
        props.setProperty("age", age);
        return props;
    }


}
