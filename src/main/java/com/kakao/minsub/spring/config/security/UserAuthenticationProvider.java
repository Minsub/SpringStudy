package com.kakao.minsub.spring.config.security;

import com.kakao.minsub.spring.model.User;
import com.kakao.minsub.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class UserAuthenticationProvider implements AuthenticationProvider {
    
    @Autowired
    private UserService userService;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User authUser = (User) authentication.getPrincipal();
        boolean isJobUser = authUser.accessKey != null && authUser.accessKey.equals("job");
        User user = userService.findOne(authUser.username);
        return new PreAuthenticatedAuthenticationToken(authUser, null, user.authorities);
    }
    
    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
