package com.kakao.minsub.spring.config.jersey.mapper;

import com.kakao.minsub.spring.config.jersey.ErrorResponseGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class DefaultExceptionMapper implements ExceptionMapper<Exception> {
    
    @Autowired
    ErrorResponseGenerator errorResponseGenerator;
    
    @Override
    public Response toResponse(Exception e) {
        return errorResponseGenerator.generate(Response.Status.INTERNAL_SERVER_ERROR, e);
    }
}
