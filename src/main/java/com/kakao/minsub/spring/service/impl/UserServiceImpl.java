package com.kakao.minsub.spring.service.impl;

import com.google.common.collect.Lists;
import com.kakao.minsub.spring.model.Authority;
import com.kakao.minsub.spring.model.AuthorityId;
import com.kakao.minsub.spring.model.User;
import com.kakao.minsub.spring.repository.AuthorityRepository;
import com.kakao.minsub.spring.repository.UserRepository;
import com.kakao.minsub.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by kakao on 2017. 7. 25..
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User findOne(String username) {
        return userRepository.findOne(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findOne(username);
    }

    @Override
    public User save(User user) {
        user.password = new BCryptPasswordEncoder().encode(user.password);
        User savedUser = userRepository.save(user);
        Authority authority = new Authority();
        AuthorityId authorityId = new AuthorityId();
        authorityId.username = user.username;
        authorityId.authorityName = "NORMAL";
        authority.authorityId = authorityId;
        savedUser.authorities = Lists.newArrayList(authorityRepository.save(authority));
        return savedUser;
    }

    @Override
    public PasswordEncoder passwordEncoder() {
        return passwordEncoder;
    }
}
