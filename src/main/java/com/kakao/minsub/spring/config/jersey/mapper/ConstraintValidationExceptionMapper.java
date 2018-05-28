package com.kakao.minsub.spring.config.jersey.mapper;

import com.kakao.minsub.spring.config.jersey.ErrorResponseGenerator;
import com.kakao.minsub.spring.model.EntityErrorResponse;
import com.kakao.minsub.spring.model.ErrorResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.Set;

public class ConstraintValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Autowired
    ErrorResponseGenerator errorResponseGenerator;
    
    @Override
    public Response toResponse(final ConstraintViolationException exception) {
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();

        ConstraintViolation<?> singleViolation = null;
        if( !CollectionUtils.isEmpty(violations) ){
            singleViolation = violations.iterator().next();
        }

        EntityErrorResponse body = new EntityErrorResponse();
        body.code = Response.Status.BAD_REQUEST.getStatusCode();
        body.message = ExceptionUtils.getStackTrace(exception);
        if (singleViolation != null) {
            body.entityName = singleViolation.getLeafBean().getClass().getSimpleName();
            body.columnName = singleViolation.getPropertyPath().toString();
            body.entityMessage = singleViolation.getMessage();
        }

        return errorResponseGenerator.generate(Response.Status.BAD_REQUEST, body);
    }
}