package com.kakao.minsub.spring.config.jersey.filter;

import com.kakao.minsub.spring.config.type.RequestLogPropertyType;
import com.kakao.minsub.spring.model.RequestLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.container.*;
import javax.ws.rs.core.Context;
import java.io.IOException;

public class RequestLogFilter implements ContainerRequestFilter, ContainerResponseFilter {
    private final Logger logger = LoggerFactory.getLogger(RequestLogFilter.class);
    
    @Autowired
    private RequestLogHelper requestLogHelper;
    
    @Context
    protected ResourceInfo resourceInfo;
    
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        try {
            requestContext.setProperty(RequestLogPropertyType.startAt.getContextValue(), System.currentTimeMillis());
            requestContext.setProperty(RequestLogPropertyType.body.getContextValue(), requestLogHelper.getEntityBody(requestContext));
        } catch (Exception e) {
            logger.error("request logging prepare error", e);
        }
    }
    
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        try {
            RequestLog log = requestLogHelper.generateRequestLog(requestContext, responseContext, resourceInfo);
            requestLogHelper.logging(log);
        } catch (Exception e) {
            logger.error("response logging prepare error", e);
        }
    }
}
