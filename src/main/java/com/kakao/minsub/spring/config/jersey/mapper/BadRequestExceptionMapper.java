package com.kakao.minsub.spring.config.jersey.mapper;

import com.kakao.minsub.spring.config.jersey.ErrorResponseGenerator;
import com.kakao.minsub.spring.exception.BadRequestException;
import com.kakao.minsub.spring.model.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> {

    @Autowired
    ErrorResponseGenerator errorResponseGenerator;
    
    @Override
    public Response toResponse(final BadRequestException exception) {

        ErrorResponse body = new ErrorResponse();
        body.code = Response.Status.BAD_REQUEST.getStatusCode();
        body.message = exception.getMessage();
        
        return errorResponseGenerator.generate(Response.Status.BAD_REQUEST, body);
    }
}