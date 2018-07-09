package com.kakao.minsub.spring.config.jersey.mapper;

import com.kakao.minsub.spring.config.jersey.ErrorResponseGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {
    
    @Autowired
    ErrorResponseGenerator errorResponseGenerator;
    
    @Override
    public Response toResponse(NotFoundException e) {
        return errorResponseGenerator.generate(Response.Status.NOT_FOUND, e);
    }
}
