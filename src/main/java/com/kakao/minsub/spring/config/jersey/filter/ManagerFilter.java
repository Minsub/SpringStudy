package com.kakao.minsub.spring.config.jersey.filter;

import com.kakao.minsub.spring.config.annotation.FeatureSupported.FeatureType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import java.util.EnumSet;

public class ManagerFilter extends DynamicFeatureSupportedFilter {
    private final Logger logger = LoggerFactory.getLogger(ManagerFilter.class);
    
    @Override
    public void filterRequest(ContainerRequestContext requestContext) {
        logger.debug("ManagerFilter: request. headers: {}", requestContext.getHeaders());
    }
    
    @Override
    public void filterResponse(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        logger.debug("ManagerFilter: response. entities: {}", responseContext.getEntity());
    }
    
    @Override
    protected EnumSet<FeatureType> supportedFeatures() {
        return EnumSet.of(FeatureType.manager);
    }
}
