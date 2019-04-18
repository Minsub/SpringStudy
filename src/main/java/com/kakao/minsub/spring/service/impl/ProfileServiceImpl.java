package com.kakao.minsub.spring.service.impl;

import com.kakao.minsub.spring.config.validator.FullValidation;
import com.kakao.minsub.spring.listener.ProfileUpdateEvent;
import com.kakao.minsub.spring.model.Profile;
import com.kakao.minsub.spring.repository.ProfileRepository;
import com.kakao.minsub.spring.service.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final Logger logger = LoggerFactory.getLogger(ProfileServiceImpl.class);

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private Validator validator;
    
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Override
    public Profile findOne(int profileId) {
        return profileRepository.findOneById(profileId).orElseThrow(RuntimeException::new);
    }

    @Override
    public Profile save(Profile profile) {
        validateProfile(profile);
        Profile savedProfile = profileRepository.save(profile);
        eventPublisher.publishEvent(new ProfileUpdateEvent(savedProfile));
        return savedProfile;
    }
    
    @Override
    public Profile update(Profile profile) {
        Profile currentProfile = findOne(profile.getId());
        currentProfile.setName(profile.getName());
        currentProfile.setSearchId(profile.getSearchId());
        validateProfile(currentProfile);
        Profile savedProfile = profileRepository.save(currentProfile);
        eventPublisher.publishEvent(new ProfileUpdateEvent(savedProfile));
        return savedProfile;
    }
    
    @Override
    public void delete(Integer id) {
        profileRepository.deleteById(id);
    }
    
    private void validateProfile(Profile profile) {
        Set<ConstraintViolation<Profile>> violations = validator.validate(profile, FullValidation.class);
        if (!violations.isEmpty()) {
            logger.error(violations.toString());
            throw new ConstraintViolationException(violations);
        }
    }
}
