package com.kakao.minsub.spring.config.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@PropertySource("classpath:common-${spring.profiles.active}.properties")
@EnableJpaRepositories(
        basePackages = { "com.kakao.minsub.spring.repository"},
        entityManagerFactoryRef = "localMysqlFactoryBean",
        transactionManagerRef = "localMysqlTransactionManager"
)
public class LocalMysqlConfig {
    @Autowired
    private JpaProperties jpaProperties;

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "db.local_mysql")
    public HikariConfig getHikariConfig() {
        return new HikariConfig();
    }

    @Primary
    @Bean(name="localMysqlDataSource")
    public DataSource getDataSource() {
        return new HikariDataSource(getHikariConfig());
    }

    @Primary
    @Bean(name="localMysqlFactoryBean")
    public LocalContainerEntityManagerFactoryBean getFactoryBean(EntityManagerFactoryBuilder builder) throws IOException {
        return builder
                .dataSource(getDataSource())
                .packages("com.kakao.minsub.spring.model")
                .persistenceUnit("localMysql")
                .properties(jpaProperties.getHibernateProperties(getDataSource()))
                .build();
    }

    @Primary
    @Bean(name="localMysqlTransactionManager")
    public PlatformTransactionManager getTransactionManager(EntityManagerFactoryBuilder builder) throws IOException {
        return new JpaTransactionManager(getFactoryBean(builder).getObject());
    }
    
    @Primary
    @Bean(name="localMysqlTransactionTemplate")
    public TransactionTemplate transactionTemplate(EntityManagerFactoryBuilder builder) throws IOException {
        return new TransactionTemplate(getTransactionManager(builder));
    }


}
