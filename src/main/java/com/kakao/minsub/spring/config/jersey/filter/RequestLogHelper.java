package com.kakao.minsub.spring.config.jersey.filter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.kakao.minsub.spring.config.type.RequestLogPropertyType;
import com.kakao.minsub.spring.model.ErrorResponse;
import com.kakao.minsub.spring.model.RequestLog;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.internal.InternalServerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.RequestDispatcher;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;

@Component
public class RequestLogHelper {
    private final Logger logger = LoggerFactory.getLogger(RequestLogHelper.class);
    private final Logger requestLogger = LoggerFactory.getLogger("request_log");
    
    protected final FastDateFormat dateFormat = FastDateFormat.getInstance("yyyyMMdd");
    protected final FastDateFormat timeFormat = FastDateFormat.getInstance("HHmmss");
    
    private static final ObjectMapper logObjectMapper = new ObjectMapper();
    protected static final ObjectMapper objectMapper
            = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false)
            .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
            .setPropertyNamingStrategy(
                    PropertyNamingStrategy.SNAKE_CASE);
    
    
    public RequestLog generateRequestLog(ContainerRequestContext requestContext, ContainerResponseContext responseContext, ResourceInfo resourceInfo) throws IOException {
        Long startAt = (Long) requestContext.getProperty(RequestLogPropertyType.startAt.getContextValue());
        if( startAt == null ) { // in case of 404 error, __startAt__ is not set
            startAt = System.currentTimeMillis();
        }
    
        RequestLog log = new RequestLog();
        log.date = dateFormat.format( startAt );
        log.time = timeFormat.format( startAt );
        log.ts = startAt;
        log.method = requestContext.getMethod();
    
        String errorRequestUri = (String) requestContext.getProperty(RequestDispatcher.ERROR_REQUEST_URI);
        log.path = errorRequestUri == null ? requestContext.getUriInfo().getAbsolutePath().getPath() : errorRequestUri;
        String query = requestContext.getUriInfo().getRequestUri().getRawQuery();
        if( StringUtils.isNotBlank(query) ){
            log.path += ( "?" + query );
        }
    
        log.from = requestContext.getUriInfo().getQueryParameters().getFirst("from");
        log.elapsed = System.currentTimeMillis() - startAt;
        log.status = responseContext.getStatus();
        log.lang = LocaleContextHolder.getLocale().getLanguage();
        log.body = (String) requestContext.getProperty(RequestLogPropertyType.body.getContextValue());
    
        if (responseContext.getStatus() == HttpStatus.BAD_REQUEST.value()) {
            ErrorResponse errorResponse = (ErrorResponse) responseContext.getEntity();
            if (errorResponse != null) {
                log.errCd = errorResponse.code;
            }
        }
    
        // 404 error -> cannot detect resource & method name
        if( resourceInfo.getResourceClass() == null ){
            log.rs = "unknown";
            log.rsm = "unknown";
        }else{
            log.rs = resourceInfo.getResourceClass().getSimpleName();
            log.rsm = resourceInfo.getResourceMethod().getName();
        }
        
        return log;
    }
    
    public String getEntityBody(ContainerRequestContext requestContext) throws JsonProcessingException {
        if (MediaType.MULTIPART_FORM_DATA_TYPE.isCompatible(requestContext.getMediaType())) {
            return "*** mutlipart form data ****";
        }

        if (MediaType.APPLICATION_FORM_URLENCODED_TYPE.equals(requestContext.getMediaType())) {
            Form form = (Form) requestContext.getProperty(InternalServerProperties.FORM_DECODED_PROPERTY);
            if (form == null || form.asMap() == null) {
                return null;
            }
    
            return logObjectMapper.writeValueAsString(form.asMap());
        }

        if (requestContext.hasEntity()) {
            ContainerRequest cr = (ContainerRequest) requestContext;
            if (cr != null && cr.bufferEntity()) {
                try {
                    return logObjectMapper.writeValueAsString(cr.readEntity(Map.class));
                } catch (Exception e) {
                    logger.warn("cannot readEntity. {} {}", requestContext.getMethod(), requestContext.getUriInfo().getPath());
                    return cr.readEntity(String.class);
                }
            }
        }
        
        return null;
    }
    
    public void logging(RequestLog log) {
        try {
            requestLogger.info(objectMapper.writeValueAsString(log));
        } catch (Exception e) {
            logger.warn("request logging failed.", e);
        }
    }
    
}
