package com.kakao.minsub.spring;

import com.kakao.minsub.spring.model.Authority;
import com.kakao.minsub.spring.model.Post;
import com.kakao.minsub.spring.model.User;
import com.kakao.minsub.spring.repository.AuthorityRepository;
import com.kakao.minsub.spring.service.PostService;
import com.kakao.minsub.spring.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Collection;
import java.util.Properties;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class SampleCodeTest {

    @Autowired
    @Qualifier(value = "testProps")
    private Properties testProps;



    @Test
    public void test() {
        testProps.stringPropertyNames().forEach(s -> System.err.println(s + ": " + testProps.getProperty(s)));


    }

}
