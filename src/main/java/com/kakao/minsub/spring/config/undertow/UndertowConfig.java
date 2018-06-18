package com.kakao.minsub.spring.config.undertow;

import io.undertow.servlet.api.DeploymentInfo;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.undertow.UndertowDeploymentInfoCustomizer;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UndertowConfig {
    
    @Bean
    public EmbeddedServletContainerCustomizer undertowCustomizer() {
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                if (container instanceof UndertowEmbeddedServletContainerFactory) {
                    ((UndertowEmbeddedServletContainerFactory) container).addDeploymentInfoCustomizers(undertowDeploymentInfoCustomizer());
                }
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
