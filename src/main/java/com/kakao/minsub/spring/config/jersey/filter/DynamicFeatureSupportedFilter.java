package com.kakao.minsub.spring.config.jersey.filter;

import com.kakao.minsub.spring.config.annotation.FeatureSupported;
import com.kakao.minsub.spring.config.annotation.FeatureSupported.FeatureType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.*;
import javax.ws.rs.core.Context;
import java.io.IOException;
import java.util.EnumSet;

public abstract class DynamicFeatureSupportedFilter implements ContainerRequestFilter, ContainerResponseFilter {
    private final Logger logger = LoggerFactory.getLogger(DynamicFeatureSupportedFilter.class);
    
    @Context
    protected ResourceInfo resourceInfo;
    
    protected abstract EnumSet<FeatureType> supportedFeatures();
    protected void filterRequest(ContainerRequestContext requestContext) {}
    protected void filterResponse(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {}
    
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        FeatureSupported featureSupported = resourceInfo.getResourceClass().getAnnotation(FeatureSupported.class);
        FeatureType featureType = featureSupported == null ? FeatureType.undefined : featureSupported.value();
        if (supportedFeatures().contains(featureType)) {
            if(logger.isDebugEnabled()) {
                logger.debug("apply filter: request {}", this.getClass().getName());
            }
            filterRequest(requestContext);
        }
    }
    
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        FeatureSupported featureSupported = resourceInfo.getResourceClass().getAnnotation(FeatureSupported.class);
        FeatureType featureType = featureSupported == null ? FeatureType.undefined : featureSupported.value();
        if (supportedFeatures().contains(featureType)) {
            if(logger.isDebugEnabled()) {
                logger.debug("apply filter: response {}", this.getClass().getName());
            }
            filterResponse(requestContext, responseContext);
        }
    }
    
    
}
