package com.kakao.minsub.spring.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Application;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

//@Component
public class SwaggerConfig /*extends Application */ {

    public SwaggerConfig() {
        super();
//        BeanConfig beanConfig = new BeanConfig();
//        beanConfig.setVersion("1.0.2");
//        beanConfig.setSchemes(new String[]{"http"});
//        beanConfig.setHost("localhost:8008");
//        beanConfig.setBasePath("/api");
//        beanConfig.setResourcePackage("com.kakao.minsub.spring");
//        beanConfig.setScan(true);
//
//        final ObjectMapper mapper = new ObjectMapper();
//        try {
//            mapper.readValue(getClass().getResource("/bean-config.json"), BeanConfig.class);
//        } catch (final IOException ioe) {
//            ioe.printStackTrace(System.err);
//        }
    }

//    @Override
//    public Set<Class<?>> getClasses() {
//        final Set<Class<?>> resources = new HashSet<>();
//
//        resources.add(ApiListingResource.class);
//        resources.add(SwaggerSerializers.class);
//        return resources;
//    }
}
