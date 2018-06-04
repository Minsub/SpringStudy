package com.kakao.minsub.spring.listener;

import com.kakao.minsub.spring.model.Profile;

public class ProfileUpdateEvent implements KafkaEvent<Profile> {
    private static final String TYPE = "profile";
    private Profile profile;
    
    public ProfileUpdateEvent(Profile profile) {
        this.profile = profile;
    }
    
    @Override
    public Profile getTarget() {
        return this.profile;
    }
    
    @Override
    public String getTargetType() {
        return TYPE;
    }
}
