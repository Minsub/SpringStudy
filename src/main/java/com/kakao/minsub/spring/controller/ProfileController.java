package com.kakao.minsub.spring.controller;


import com.kakao.minsub.spring.model.Profile;
import com.kakao.minsub.spring.service.ProfileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Api("Profile")
@Path("/profile")
@Produces(MediaType.APPLICATION_JSON)
public class ProfileController {

    @Autowired
    private ProfileService profileService;
    
    @ApiOperation("프로필 가져오기")
    @GET
    @Path("/{id}")
    public Profile show(@PathParam("id") final int id) {
        return profileService.findOne(id);
    }
    
    
    @ApiOperation("프로필 생성하기")
    @POST
    @Path("/create")
    public Profile create(final Profile profile) {
        return profileService.save(null, profile.getName(), profile.getSearchId());
    }
    
    @ApiOperation("프로필 업데이트")
    @PUT
    @Path("/{id}")
    public Profile update(@PathParam("id") final int id, final Profile profile) {
        return profileService.save(id, profile.getName(), profile.getSearchId());
    }
    
    @ApiOperation("프로필 지우기")
    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") final int id) {
        profileService.delete(id);
    }
}
