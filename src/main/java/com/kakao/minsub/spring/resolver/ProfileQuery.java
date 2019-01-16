package com.kakao.minsub.spring.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.kakao.minsub.spring.model.Profile;
import com.kakao.minsub.spring.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProfileQuery implements GraphQLQueryResolver {

    @Autowired
    private ProfileRepository profileRepository;

    public Profile getProfile(Integer id) {
        return profileRepository.findOneById(id).orElseThrow(RuntimeException::new);
    }
}
