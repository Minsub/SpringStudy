package com.kakao.minsub.spring.config.jersey.mapper;

import com.kakao.minsub.spring.config.jersey.ErrorResponseGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class AccessDeniedExceptionMapper implements ExceptionMapper<AccessDeniedException> {
    
    @Autowired
    ErrorResponseGenerator errorResponseGenerator;
    
    @Override
    public Response toResponse(AccessDeniedException e) {
        return errorResponseGenerator.generate(Response.Status.FORBIDDEN, e);
    }
}
