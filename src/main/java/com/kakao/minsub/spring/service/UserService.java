package com.kakao.minsub.spring.service;

import com.kakao.minsub.spring.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by kakao on 2017. 7. 25..
 */
public interface UserService extends UserDetailsService {
    public User findOne(String username);

    public User save(User user);

    public PasswordEncoder passwordEncoder();
}
