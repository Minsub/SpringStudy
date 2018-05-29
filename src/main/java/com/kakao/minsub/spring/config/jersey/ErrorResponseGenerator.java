package com.kakao.minsub.spring.config.jersey;

import com.kakao.minsub.spring.model.ErrorResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.Optional;

@Component
public class ErrorResponseGenerator {

    public Response generate(final Status status, final Exception e) {
        ErrorResponse body = new ErrorResponse();
        body.code = Optional.ofNullable(status).orElse(Status.INTERNAL_SERVER_ERROR).getStatusCode();
        body.message = ExceptionUtils.getStackTrace(e);
        
        return Response.status(status).entity(body).build();
    }
    
    public Response generate(final Status status, final ErrorResponse body) {
        return Response.status(status).entity(body).build();
    }
    
    public Response generate(final Status status, final AccessDeniedException e) {
        return Response.status(status).entity("").build();
    }
}
