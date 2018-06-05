package com.kakao.minsub.spring.service;

import com.kakao.minsub.spring.model.Profile;

/**
 * Created by kakao on 2017. 7. 14..
 */
public interface ProfileService {

    public Profile findOne(int profileId);

    public Profile save(Profile profile);
    
    public Profile update(Profile profile);

    public void delete(Integer id);
}
