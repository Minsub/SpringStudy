package com.kakao.minsub.spring.config;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.kakao.minsub.spring.config.mapper.ConstraintValidationExceptionMapper;
import com.kakao.minsub.spring.controller.*;
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
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig  extends ResourceConfig {

    public JerseyConfig() {
        property(ServletProperties.FILTER_STATIC_CONTENT_REGEX, "/(static|public)/.*");
        registerEndpoints();
        configureSwagger();
    }
    
    private void registerEndpoints() {
//        packages("com.kakao.minsub.spring.controller");
        register(HomeController.class);
        register(PostController.class);
        register(ProfileController.class);
        register(RestController.class);
        register(TestController.class);
        register(UserController.class);

        register(ConstraintValidationExceptionMapper.class);
        register(FreemarkerMvcFeature.class);
        register(RolesAllowedDynamicFeature.class);
        register(WadlResource.class);
    }
    
    private void configureSwagger() {
//        final Resource.Builder builder = Resource.builder(ApiListingResource.class);
//        builder.path("swagger.{type:json|yaml}");
//        final Resource apiListingResource = builder.build();
//        registerResources(apiListingResource);
//        register(SwaggerSerializers.class);
    
        this.register(ApiListingResource.class);
        this.register(SwaggerSerializers.class);
    
        BeanConfig config = new BeanConfig();
        config.setTitle("POC - Restful API by Spring Boot, Jersey, Swagger");
        config.setVersion("v1");
        config.setContact("gray.ji");
        config.setSchemes(new String[] { "http", "https" });
        config.setBasePath("/");
        config.setResourcePackage("com.kakao.minsub.spring.controller");
        config.setPrettyPrint(true);
        config.setScan(true);
        
        
        // https://github.com/swagger-api/swagger-ui
        // https://github.com/brightzheng100/springboot-jersey-swagger
    }
    
}
