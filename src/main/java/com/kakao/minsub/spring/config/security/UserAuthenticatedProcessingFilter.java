package com.kakao.minsub.spring.config.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;

public class UserAuthenticatedProcessingFilter extends AbstractPreAuthenticatedProcessingFilter {
    public UserAuthenticatedProcessingFilter(AuthenticationManager authenticationManager) {
        setAuthenticationManager(authenticationManager);
    }
    
    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        return null;
    }
    
    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return null;
    }
}
