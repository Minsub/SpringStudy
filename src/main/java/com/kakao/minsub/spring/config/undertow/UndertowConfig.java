package com.kakao.minsub.spring.config.undertow;

import io.undertow.servlet.api.DeploymentInfo;
import org.springframework.boot.web.embedded.undertow.UndertowDeploymentInfoCustomizer;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UndertowConfig {

    @Bean
    public WebServerFactoryCustomizer<UndertowServletWebServerFactory> undertowCustomizer() {
        return new WebServerFactoryCustomizer<UndertowServletWebServerFactory>() {
            @Override
            public void customize(UndertowServletWebServerFactory factory) {
                factory.addDeploymentInfoCustomizers(undertowDeploymentInfoCustomizer());
            }
        };
    }

    @Bean
    public UndertowDeploymentInfoCustomizer undertowDeploymentInfoCustomizer() {
        return new UndertowDeploymentInfoCustomizer() {
            @Override
            public void customize(DeploymentInfo deploymentInfo) {
                deploymentInfo.addOuterHandlerChainWrapper(undertowShutdownHandlerWrapper());
            }
        };
    }
    
    @Bean
    public UndertowShutdownHandlerWrapper undertowShutdownHandlerWrapper() {
        return new UndertowShutdownHandlerWrapper();
    }
}
