package com.kakao.minsub.spring.controller;

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

@Path("/test/async")
@Api("Test: Async")
@Produces(MediaType.APPLICATION_JSON)
public class AsyncController {
    private final Logger logger = LoggerFactory.getLogger(AsyncController.class);
    
    @Autowired
    private TestService testService;
    
    @GET
    @Path("/print")
    public Map print() {
        logger.debug("Async Print START on Controller @@@@@@");
        testService.asyncPrint(7);
        testService.asyncPrint(7);
        testService.asyncPrint(7);
        logger.debug("Async Print END on Controller @@@@@@");
        return Collections.emptyMap();
    }
    
    
    @GET
    @Path("/print/inner")
    public Map innterAsync() {
        logger.debug("Async Inner Print START on Controller @@@@@@");
        testService.print(7);
        logger.debug("Async Inner Print END on Controller @@@@@@");
        return Collections.emptyMap();
    }
}
