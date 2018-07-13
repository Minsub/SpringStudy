package com.kakao.minsub.spring.config.security;

import com.kakao.minsub.spring.model.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class UserAuthenticatedProcessingFilter extends AbstractPreAuthenticatedProcessingFilter {
    public UserAuthenticatedProcessingFilter(AuthenticationManager authenticationManager) {
        setAuthenticationManager(authenticationManager);
    }
    
    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        User user = new User();
        user.username = "admin";
        user.accessKey = getCookie(request, "_job");
        return user;
    }
    
    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return null;
    }
    
    private String getCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie: cookies) {
            if (cookieName.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
