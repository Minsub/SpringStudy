package com.kakao.minsub.spring.config;

import com.kakao.minsub.spring.config.mapper.ConstraintValidationExceptionMapper;
import com.kakao.minsub.spring.controller.*;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature;
import org.glassfish.jersey.server.wadl.internal.WadlResource;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        property(ServletProperties.FILTER_STATIC_CONTENT_REGEX, "/(static|public)/.*");
        registerEndpoints();
        configureSwagger();
    }
    
    private void registerEndpoints() {
        // jar로 export해서 실행시 packages로 등록 불가
        //packages("com.kakao.minsub.spring.controller");
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

    /*
    https://github.com/swagger-api/swagger-ui 에서 dist를 받아 resource/static/swagger로 복사
    localhost:8080/static/swagger/index.html
     */
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

        // https://github.com/swagger-api/swagger-ui
        // https://github.com/brightzheng100/springboot-jersey-swagger
    }
    
}
