package com.kakao.minsub.spring.controller;


import com.kakao.minsub.spring.model.Profile;
import com.kakao.minsub.spring.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/profile")
@Produces(MediaType.APPLICATION_JSON)
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GET
    @Path("/{id}")
    public Profile show(@PathParam("id") final int id) {
        return profileService.findOne(id);
    }


    @POST
    @Path("/create")
    public Profile create(final Profile profile) {
        return profileService.save(null, profile.getName(), profile.getSearchId());
    }

    @PUT
    @Path("/{id}")
    public Profile update(@PathParam("id") final int id, final Profile profile) {
        return profileService.save(id, profile.getName(), profile.getSearchId());
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") final int id) {
        profileService.delete(id);
    }
}
