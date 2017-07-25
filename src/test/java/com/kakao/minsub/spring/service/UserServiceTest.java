package com.kakao.minsub.spring.service;

import com.kakao.minsub.spring.model.Authority;
import com.kakao.minsub.spring.model.User;
import com.kakao.minsub.spring.repository.AuthorityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Collection;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@EnableAutoConfiguration
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Test
    public void user() {
        User user = userService.findOne("admin");
        System.out.println(user);
    }

    @Test
    public void authority() {
        Collection<Authority> list = authorityRepository.findAllByUsername("admin");
        list.forEach(a -> System.out.println(a.authorityId .username+ ": " + a.authorityId.authorityName));

        GrantedAuthority a;
    }
}
