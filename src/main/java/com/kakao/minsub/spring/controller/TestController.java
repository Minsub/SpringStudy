package com.kakao.minsub.spring.controller;

import com.google.common.collect.Maps;
import com.kakao.minsub.spring.sample.BeanLifeCycle;
import com.kakao.minsub.spring.service.TestService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Path("/test")
@Api("Test")
@Produces(MediaType.APPLICATION_JSON)
public class TestController {
    private final Logger logger = LoggerFactory.getLogger(TestController.class);
    
    @Autowired
    private TestService testService;
    
    @Autowired
    private BeanLifeCycle beanLifeCycle;
    
    @GET
    @Path("/jpa")
    public Map jpa() {
        testService.testJPA();
        return Collections.emptyMap();
    }
    
    @GET
    @Path("/version")
    public Map version() {
        Map<String, Object> result = Maps.newHashMap();
        result.put("version", 1);
    
        beanLifeCycle.print();
        
        return result;
    }
    
    @GET
    @Path("empty")
    public void empty() {
    
    }
    
    @GET
    @Path("gracefull")
    public void gracefull() throws Exception {
        logger.info("START graceful test");
    
        TimeUnit.SECONDS.sleep(5);
        
        logger.info("END graceful test");
    }

}
