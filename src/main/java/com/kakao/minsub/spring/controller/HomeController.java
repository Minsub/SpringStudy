package com.kakao.minsub.spring.controller;


import org.glassfish.jersey.server.mvc.Viewable;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by kakao on 2017. 7. 27..
 */

@Path("/page")
@Produces(MediaType.TEXT_HTML)
public class HomeController {

    @GET
    @Path("/index")
    public Viewable index() {
        return new Viewable("/templates/index.ftl");
    }

    @GET
    @Path("/api")
    public Viewable api() {
        return new Viewable("/templates/api.ftl");
    }

    @GET
    @Path("/admin")
    @RolesAllowed({"ROLE_ADMIN"})
    public String adminOnly() {
        return "Admin Only Page";
    }


    @GET
    @Path("/normal")
    @RolesAllowed({"ROLE_NORMAL"})
    public String normalOnly() {
        return "Normal Only Page";
    }

}
