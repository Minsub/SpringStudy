package com.kakao.minsub.spring.config;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.kakao.minsub.spring.config.mapper.ConstraintValidationExceptionMapper;
import io.swagger.converter.ModelConverters;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import io.swagger.util.Json;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature;
import org.glassfish.jersey.server.wadl.internal.WadlResource;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig  extends ResourceConfig {

    public JerseyConfig() {
        registerEndpoints();
        configureSwagger();
    }
    
    private void registerEndpoints() {
        packages("com.kakao.minsub.spring.controller");
    
        register(ConstraintValidationExceptionMapper.class);
        register(FreemarkerMvcFeature.class);
        register(RolesAllowedDynamicFeature.class);
        register(WadlResource.class);
    }
    
    private void configureSwagger() {
        register(ApiListingResource.class);
//        register(SwaggerSerializers.class);

        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setConfigId("springStudy-swagger-example");
        beanConfig.setTitle("springStudy-swagger");
        beanConfig.setVersion("1.0.2");
        beanConfig.setSchemes(new String[]{"http","https"});
        beanConfig.setHost("localhost:8888");
//        beanConfig.setBasePath("/api");
        beanConfig.setResourcePackage("com.kakao.minsub.spring.controller");
        beanConfig.setPrettyPrint(true);
        beanConfig.setScan(true);
    }
    
}
