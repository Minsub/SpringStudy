package com.kakao.minsub.spring.service.impl;

import com.kakao.minsub.spring.repository.ProfileRepository;
import com.kakao.minsub.spring.sample.CloudConfigs;
import com.kakao.minsub.spring.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.concurrent.TimeUnit;

@Service
@RefreshScope
@EnableConfigurationProperties(CloudConfigs.class)
public class TestServiceImpl implements TestService {
    private final Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private ProfileRepository profileRepository;
    
    @Value("${cloud.greeting:Default Greeting}")
    private String greeting;
    
    @Autowired
    private CloudConfigs cloudConfigs;
    
    @Override
    @Transactional
    public void testJPA() {
        try {
            logger.debug("START JPA TEST 11");
//            entityManager.getTransaction().begin();

//            Profile profile = new Profile();
//            profile.setName("gray.ji@kakao.com");
//            profile.setSearchId("gray");
//            entityManager.persist(profile);

//            Query query = entityManager.createNativeQuery("SELECT * FROM profiles");
//            System.out.println(query.getResultList());

            
//            Profile profile = entityManager.find(Profile.class, 56134);
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
            logger.debug("FINISH JPA TEST");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public int getInt(int count) {
        return count;
    }

    @Override
    public void print(int count) {
        logger.debug("INNER START");
        printThead(count);
        logger.debug("INNER END");
    }
    
    @Override
    @Async("simpleAsyncTaskExecutor")
    public void asyncPrint(int count) {
        logger.debug("simpleAsyncTaskExecutor print START");
        for (int i=1; i <= count; i++) {
            logger.debug("PRINT: {}", i);
            if (i == 5) {
                throw new RuntimeException("ERROR!!!");
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        logger.debug("simpleAsyncTaskExecutor print END");
    }
    
    // async가 정상동작하지 않음!
    @Async
    public void printThead(int count) {
        try {
            for (int i=0; i<count; i++) {
                logger.debug(String.format("Count: %s", i));
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public String getGreeting() {
        return String.format("static Value: %s, bean Binding: %s", greeting, cloudConfigs.getGreeting());
//        return String.format("static Value: %s", greeting);
    }
}
