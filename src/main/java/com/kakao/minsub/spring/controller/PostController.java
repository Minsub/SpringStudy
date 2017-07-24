package com.kakao.minsub.spring.controller;


import com.kakao.minsub.spring.model.Post;
import com.kakao.minsub.spring.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.List;


@Path("/post")
@Produces(MediaType.APPLICATION_JSON)
public class PostController {

    @Autowired
    private PostService postService;

    @GET
    @Path("/{id}")
    public Post show(@PathParam("id") final int id) {
        return postService.findOne(id);
    }


    @POST
    @Path("/create")
    public Post create(final Post post) {
        return postService.save(post);
    }

    @PUT
    @Path("/{id}")
    public Post update(@PathParam("id") final int id, final Post post) {
        return postService.save(post);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") final int id) {
        postService.delete(id);
    }

    @GET
    @Path("/all")
    public Collection<Post> showAll() {
        return postService.findAvailablePosts();
    }
}
