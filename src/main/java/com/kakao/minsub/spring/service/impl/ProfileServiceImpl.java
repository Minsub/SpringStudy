package com.kakao.minsub.spring.service.impl;

import com.kakao.minsub.spring.config.validator.FullValidation;
import com.kakao.minsub.spring.controller.PostController;
import com.kakao.minsub.spring.model.Profile;
import com.kakao.minsub.spring.repository.ProfileRepository;
import com.kakao.minsub.spring.service.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

@Component
public class ProfileServiceImpl implements ProfileService {
    private final Logger logger = LoggerFactory.getLogger(ProfileServiceImpl.class);

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private Validator validator;

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

        Set<ConstraintViolation<Profile>> violations = validator.validate(profile, FullValidation.class);
        if (!violations.isEmpty()) {
            logger.error(violations.toString());
            throw new ConstraintViolationException(violations);
        }

        return profileRepository.save(profile);
    }

    @Override
    public void delete(Integer id) {
        profileRepository.delete(id);
    }
}
