package com.kakao.minsub.spring.config.security;

import com.google.common.collect.Lists;
import com.kakao.minsub.spring.model.Authority;
import com.kakao.minsub.spring.model.AuthorityId;
import com.kakao.minsub.spring.model.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;

public class DefaultAuthenticatedProcessingFilter extends AbstractPreAuthenticatedProcessingFilter {
    public DefaultAuthenticatedProcessingFilter(AuthenticationManager authenticationManager) {
        setAuthenticationManager(authenticationManager);
    }
    
    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        User authUser = new User();
        authUser.name = "admin";
        authUser.password = "admin";
        Authority authority = new Authority();
        AuthorityId id = new AuthorityId();
        id.setAuthorityName("ADMIN");
        authority.authorityId = id;
        authUser.authorities = Lists.newArrayList(authority);
        return authUser;
    }
    
    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return null;
    }
}
