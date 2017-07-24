package com.kakao.minsub.spring.controller;


import com.kakao.minsub.spring.model.Post;
import com.kakao.minsub.spring.service.PostService;
import com.kakao.minsub.spring.util.TimeControll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;


@EnableCaching
@Path("/post")
@Produces(MediaType.APPLICATION_JSON)
public class PostController {

    private final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private PostService postService;

    @GET
    @Path("/{id}")
    public Post show(@PathParam("id") final int id) {
        TimeControll.startCheckPoint("postNoCache");
        Post post = postService.findOne(id);
        logger.info(TimeControll.endCheckPoint("postNoCache"));
        return post;
    }

    @GET
    @Path("/cache/{id}")
    public Post showWithCache(@PathParam("id") final int id) {
        TimeControll.startCheckPoint("postCache");
        Post post = postService.findOneCache(id);
        logger.info(TimeControll.endCheckPoint("postCache"));
        return post;
    }

    @GET
    @Path("/refresh/{id}")
    public void refreshCache(@PathParam("id") final int id) {
        logger.info(id + " cache clear!!");
        postService.refreshCache(id);
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
    @Path("/available")
    public Collection<Post> showAll() {
        return postService.findAvailablePosts();
    }

    @GET
    @Path("/all")
    public Page<Post> showPageable(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size=2) Pageable page) {
        return postService.findAll(page);
    }
}
