package com.kakao.minsub.spring.controller;

import com.google.common.collect.Maps;
import org.glassfish.jersey.server.mvc.Viewable;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/page")
@Produces(MediaType.TEXT_HTML)
public class HomeController {
    
    @GET
    @Path("/login")
    public Viewable login() {
        return new Viewable("/templates/login.ftl");
    }
    
    @POST
    @Path("/login")
    public Viewable login(@Context HttpServletRequest request, @Context HttpServletResponse response) {
       return new Viewable("/templates/index.ftl");
    }

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
    @Path("/test")
    public Viewable test() {
        return new Viewable("/templates/api.ftl");
    }

    @GET
    @Path("/test2")
    public RedirectView test2() {
        return new RedirectView("http://localhost:8080/page/index");
    }

    @GET
    @Path("/test/view")
    public Viewable test_dept2() {
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
