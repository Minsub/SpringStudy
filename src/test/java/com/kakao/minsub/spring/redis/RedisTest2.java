package com.kakao.minsub.spring.redis;

import com.kakao.minsub.spring.model.Point;
import com.kakao.minsub.spring.repository.PointRedisRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("development")
public class RedisTest2 {
    
    @Autowired
    private PointRedisRepository pointRedisRepository;
    
    @After
    public void tearDown() throws Exception {
        pointRedisRepository.deleteAll();
    }
    
    @Test
    public void 기본_등록_조회기능() {
        //given
        String id = "gray";
        LocalDateTime refreshTime = LocalDateTime.of(2018, 5, 26, 0, 0);
        Point point = Point.builder()
                .id(id)
                .amount(1000L)
                .refreshTime(refreshTime)
                .build();
        
        //when
        pointRedisRepository.save(point);
        
        //then
        Point savedPoint = pointRedisRepository.findById(id).get();
        assertThat(savedPoint.getAmount()).isEqualTo(1000L);
        assertThat(savedPoint.getRefreshTime()).isEqualTo(refreshTime);
    }
}
