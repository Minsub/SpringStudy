package com.kakao.minsub.spring.controller;


import com.kakao.minsub.spring.service.TestService;
import com.kakao.minsub.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.Map;


@Path("/test")
@Produces(MediaType.APPLICATION_JSON)
public class TestController {

    @Autowired
    private TestService testService;

    @GET
    @Path("/sync")
    public Map sync() {
        System.out.println("START");
        testService.print(5);
        testService.print(3);
        System.out.println("END");
        return Collections.emptyMap();
    }
    
    @GET
    @Path("/jpa")
    public Map jpa() {
        testService.testJPA();
        return Collections.emptyMap();
    }

}
