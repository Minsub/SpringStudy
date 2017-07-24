package com.kakao.minsub.spring.config;

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.LocaleContextMessageInterpolator;
import org.springframework.validation.beanvalidation.MessageSourceResourceBundleLocator;

import javax.validation.MessageInterpolator;
import javax.validation.Validator;

@Configuration
public class ValidationConfig {
    @Autowired
    @Qualifier("messageSource")
    private MessageSource messageSource;

    @Bean
    public MessageInterpolator messageInterpolator(){
        return new LocaleContextMessageInterpolator(
                new ResourceBundleMessageInterpolator(
                        new MessageSourceResourceBundleLocator(messageSource)));
    }

    @Bean
    public Validator validator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setMessageInterpolator( messageInterpolator() );
        return validator;
    }
}
