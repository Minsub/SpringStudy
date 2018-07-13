package com.kakao.minsub.spring.jpa;

import com.kakao.minsub.spring.BaseDataJpaTest;
import com.kakao.minsub.spring.config.db.LocalMysqlConfig;
import com.kakao.minsub.spring.model.Profile;
import com.kakao.minsub.spring.repository.ProfileRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {LocalMysqlConfig.class})
public class ProfileJpaTest extends BaseDataJpaTest {
    
    @Autowired
    private ProfileRepository profileRepository;
    
    @Test
    public void testProfileInsert() {
        profileRepository.save(Profile.builder().name("gray.ji!!!").searchId("gray.jjjjj").build());
        System.err.println(String.format("insert and size: %d", profileRepository.findAll().size()));
    }
    
    @Test
    public void testProfileFind() {
        System.err.println(String.format("find Test size: %d", profileRepository.findAll().size()));
    }
    

}
