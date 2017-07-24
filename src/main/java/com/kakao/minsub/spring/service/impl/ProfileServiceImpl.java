package com.kakao.minsub.spring.service.impl;

import com.kakao.minsub.spring.model.Profile;
import com.kakao.minsub.spring.repository.ProfileRepository;
import com.kakao.minsub.spring.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProfileServiceImpl implements ProfileService {


    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public Profile findOne(int profileId) {
        return profileRepository.findOne(profileId);
    }

    @Override
    public Profile save(Integer id, String name, String searchId) {
        Profile profile = new Profile();
        profile.setId(id);
        profile.setName(name);
        profile.setSearchId(searchId);
        return profileRepository.save(profile);
    }

    @Override
    public void delete(Integer id) {
        profileRepository.delete(id);
    }
}
