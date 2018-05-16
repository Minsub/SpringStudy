package com.kakao.minsub.spring.service.impl;

import com.kakao.minsub.spring.model.Profile;
import com.kakao.minsub.spring.repository.ProfileRepository;
import com.kakao.minsub.spring.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.concurrent.TimeUnit;

/**
 * Created by kakao on 2017. 7. 24..
 */
@Service
public class TestServiceImpl implements TestService {
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private ProfileRepository profileRepository;
    
    @Override
    @Transactional
    public void testJPA() {
        try {
            System.out.println("START JPA TEST");
//            entityManager.getTransaction().begin();

//            Profile profile = new Profile();
//            profile.setName("gray.ji@kakao.com");
//            profile.setSearchId("gray");
//            entityManager.persist(profile);

//            Query query = entityManager.createNativeQuery("SELECT * FROM profiles");
//            System.out.println(query.getResultList());

            
            Profile profile = entityManager.find(Profile.class, 56134);
//            Profile profile = profileRepository.findOne(56134);
//            System.out.println(profile);
            
//            System.out.println(profile.getSearchId());
            
//            TimeUnit.SECONDS.sleep(5);
    
//            entityManager.detach(profile);
//            profile.setSearchId(profile.getSearchId() + "#");
//            entityManager.merge(profile);

//            entityManager.flush();
//            entityManager.getTransaction().commit();
//            entityManager.close();
            System.out.println("FINISH JPA TEST");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public int getInt(int count) {
        return count;
    }

    @Override
    @Async
    public void print(int count) {
        System.out.println("INNER START");
        printThead(count);
//        asyncDefault();
        System.out.println("INNER END");
    }
    
    @Async
    public void asyncDefault() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Default Executor name: " + Thread.currentThread().getName());
        throw new RuntimeException("asyncDefault");
    }
    
//    @Async
    public void printThead(int count) {
        try {
            for (int i=0; i<count; i++) {
                System.out.println(String.format("Count: %s", i));
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
