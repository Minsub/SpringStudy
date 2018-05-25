package com.kakao.minsub.spring.controller;


import com.kakao.minsub.spring.model.User;
import com.kakao.minsub.spring.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/user")
@Api("User")
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

    @Autowired
    private UserService userService;

    @GET
    @Path("/{username}")
    @ApiOperation("유저 정보 확인")
    public User show(@PathParam("username") final String username) {
        return userService.findOne(username);
    }

    @POST
    @Path("/create")
    public User create(final User user) {
        return userService.save(user);
    }


}
