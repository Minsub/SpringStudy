package com.kakao.minsub.spring.jpa;

import com.kakao.minsub.spring.config.db.LocalMysqlConfig;
import com.kakao.minsub.spring.model.Profile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@JdbcTest
@ContextConfiguration(classes = {LocalMysqlConfig.class})
@ActiveProfiles("development")
public class SpringJdbcTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Test
    public void jdbcTest() {
        Profile profile = jdbcTemplate.queryForObject("select * from profiles where id=1", Profile.class);
        System.err.println(profile);
    }
}
