package com.kakao.minsub.spring.config;

import com.kakao.minsub.spring.config.mapper.ConstraintValidationExceptionMapper;
import com.kakao.minsub.spring.controller.HomeController;
import com.kakao.minsub.spring.controller.PostController;
import com.kakao.minsub.spring.controller.ProfileController;
import com.kakao.minsub.spring.controller.UserController;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig  extends ResourceConfig {

    public JerseyConfig() {
        register(ProfileController.class);
        register(PostController.class);
        register(UserController.class);
        register(HomeController.class);
        register(ConstraintValidationExceptionMapper.class);
        register(FreemarkerMvcFeature.class);
    }
}
