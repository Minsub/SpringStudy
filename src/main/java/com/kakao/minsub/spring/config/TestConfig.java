package com.kakao.minsub.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Properties;

@Configuration
@PropertySource("classpath:common-production.properties")
public class TestConfig {

    @Value("${db.local_mysql.username}")
    private String name;

    @Value("${db.local_mysql.jdbcUrl}")
    private String url;

    @Bean(name="testProps")
    @ConfigurationProperties(prefix = "db.local_mysql")
    public Properties getProperties() {
        Properties props = new Properties();
        props.setProperty("name", name);
        props.setProperty("url", url);
        return props;
    }
}
