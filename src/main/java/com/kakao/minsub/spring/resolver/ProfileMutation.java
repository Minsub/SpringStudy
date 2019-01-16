package com.kakao.minsub.spring.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.kakao.minsub.spring.model.Profile;
import com.kakao.minsub.spring.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProfileMutation implements GraphQLMutationResolver {

    @Autowired
    private ProfileRepository profileRepository;

    public Profile newProfile(String name, String searchId) {
        Profile profile = new Profile();
        profile.setName(name);
        profile.setSearchId(searchId);
        return profileRepository.save(profile);
    }
}
