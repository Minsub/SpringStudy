package com.kakao.minsub.spring.config;

import com.kakao.minsub.spring.config.mapper.ConstraintValidationExceptionMapper;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

@Component
public class JerseyConfig  extends ResourceConfig {

    public JerseyConfig() {
        registerEndpoints();
//        configureSwagger();
    }
    
    private void registerEndpoints() {
        packages("com.kakao.minsub.spring.controller");
    
        register(ConstraintValidationExceptionMapper.class);
        register(FreemarkerMvcFeature.class);
//        register(RolesAllowedDynamicFeature.class);
//        register(WadlResource.class);
//        register(CORSResponseFilter.class);
    }
    
    private void configureSwagger() {
        register(ApiListingResource.class);
        register(SwaggerSerializers.class);

        BeanConfig beanConfig = new BeanConfig();
//        beanConfig.setConfigId("springStudy-swagger-example");
//        beanConfig.setTitle("springStudy-swagger");
        beanConfig.setVersion("0.0.1");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:8080");
        beanConfig.setBasePath("/v1");
        beanConfig.setResourcePackage("com.kakao.minsub.spring.controller");
        beanConfig.setPrettyPrint(true);
        beanConfig.setScan(true);
    }
    
}
