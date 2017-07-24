package com.kakao.minsub.spring.config;

import com.kakao.minsub.spring.controller.PostController;
import com.kakao.minsub.spring.controller.ProfileController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig  extends ResourceConfig {


    public JerseyConfig() {
        register(ProfileController.class);
        register(PostController.class);


    }
}
