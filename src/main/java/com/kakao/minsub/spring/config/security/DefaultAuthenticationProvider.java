package com.kakao.minsub.spring.config.security;

import com.kakao.minsub.spring.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class DefaultAuthenticationProvider implements AuthenticationProvider, Ordered {
    private final Logger logger = LoggerFactory.getLogger(DefaultAuthenticationProvider.class);
    
    private int order = -1;
    
    @Override
    public int getOrder() {
        return order;
    }
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        logger.info("@@@@@@@@: {}", authentication);
        User authUser = (User) authentication.getPrincipal();
        return new PreAuthenticatedAuthenticationToken(authUser, null, authUser.getAuthorities());
    }
    
    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
