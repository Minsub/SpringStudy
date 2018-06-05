package com.kakao.minsub.spring.config;

import com.kakao.minsub.spring.config.jersey.filter.ManagerFilter;
import com.kakao.minsub.spring.config.jersey.filter.RequestLogFilter;
import com.kakao.minsub.spring.config.jersey.mapper.AccessDeniedExceptionMapper;
import com.kakao.minsub.spring.config.jersey.mapper.ConstraintValidationExceptionMapper;
import com.kakao.minsub.spring.config.jersey.mapper.DefaultExceptionMapper;
import com.kakao.minsub.spring.controller.*;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        property(ServletProperties.FILTER_STATIC_CONTENT_REGEX, "/(static|public)/.*");
        registerEndpoints();
        registerExceptionMappers();
        registerFilters();
        configureSwagger();
    }
    
    private void registerEndpoints() {
        //packages("com.kakao.minsub.spring.controller"); // not working in executable jar mode
        register(HomeController.class);
        register(PostController.class);
        register(ProfileController.class);
        register(RestController.class);
        register(TestController.class);
        register(UserController.class);

        register(FreemarkerMvcFeature.class);
        register(RolesAllowedDynamicFeature.class);
    }
    
    private void registerExceptionMappers() {
        register(ConstraintValidationExceptionMapper.class);
        register(AccessDeniedExceptionMapper.class);
        register(DefaultExceptionMapper.class);
    }
    
    private void registerFilters() {
        register(ManagerFilter.class);
        register(RequestLogFilter.class);
    }
    private void configureSwagger() {
        register(ApiListingResource.class);
        register(SwaggerSerializers.class);
    
        BeanConfig config = new BeanConfig();
        config.setTitle("Spring Study Project");
        config.setVersion("0.0.1");
        config.setContact("gray.ji");
        config.setSchemes(new String[] { "http", "https" });
        config.setBasePath("/");
        config.setResourcePackage("com.kakao.minsub.spring.controller");
        config.setPrettyPrint(true);
        config.setScan(true);
    }
    
}
