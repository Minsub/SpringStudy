package com.kakao.minsub.spring.config.mapper;

import com.kakao.minsub.spring.model.ErrorResponse;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.Set;

public class ConstraintValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(final ConstraintViolationException exception) {
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();

        ConstraintViolation<?> singleViolation = null;
        if( !CollectionUtils.isEmpty(violations) ){
            singleViolation = violations.iterator().next();
        }

        ErrorResponse body = new ErrorResponse();
        body.code = String.valueOf(Response.Status.BAD_REQUEST.getStatusCode());
        if (singleViolation != null) {
            body.entityName = singleViolation.getLeafBean().getClass().getSimpleName();
            body.columnName = singleViolation.getPropertyPath().toString();
            body.message = singleViolation.getMessage();
        }

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(body)
                .build();
    }
}