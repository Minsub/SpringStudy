package com.kakao.minsub.spring.repository;

import com.kakao.minsub.spring.model.Profile;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends Repository<Profile, Integer> {

    Optional<Profile> findOne(Integer profileId);
    
    Profile save(Profile profile);
    
    void delete(Integer profileId);
    
    List<Profile> findAll();
}
