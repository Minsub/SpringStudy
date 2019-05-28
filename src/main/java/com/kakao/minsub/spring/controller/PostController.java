package com.kakao.minsub.spring.controller;


import com.kakao.minsub.spring.model.Post;
import com.kakao.minsub.spring.model.User;
import com.kakao.minsub.spring.service.PostService;
import com.kakao.minsub.spring.service.UserService;
import com.kakao.minsub.spring.util.TimeControll;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.List;


@EnableCaching
@Path("/post")
@Api("Post")
@Produces(MediaType.APPLICATION_JSON)
public class PostController {

    private final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @GET
    @Path("/{id}")
    public Post show(@PathParam("id") final int id) {
        TimeControll.startCheckPoint("postNoCache");
        Post post = postService.findOne(id);
        logger.info(TimeControll.endCheckPoint("postNoCache"));
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
    public Page<Post> showPageable(@QueryParam("size") @DefaultValue("3") int size,
                                   @QueryParam("page") @DefaultValue("1") int page) {
        final Pageable pageable = new PageRequest(page - 1, size, new Sort(Sort.Direction.DESC, "id"));
        return postService.findAll(pageable);
    }
    
    @GET
    @Path("/all/native")
    public Page<Post> showPageableNative(@QueryParam("size") @DefaultValue("3") int size, @QueryParam("page") @DefaultValue("1") int page) {
        final Pageable pageable = new PageRequest(page - 1, size, new Sort(Sort.Direction.DESC, "id"));
//        final Pageable pageable = new PageRequest(page - 1, size);
        return postService.findAllNative(pageable);
    }
    
    @GET
    @Path("/real_all")
    public List<Post> all() {
        return postService.findAll();
    }

    @GET
    @Path("/")
    public Page<Post> showa(@QueryParam("size") @DefaultValue("3") int size,
                                   @QueryParam("page") @DefaultValue("1") int page) {
        final Pageable pageable = new PageRequest(page , size, new Sort(Sort.Direction.DESC, "id"));
        return postService.findAll(pageable);
    }

    @GET
    @Path("/test")
    public User test() {
        return userService.findOne("admin");
    }
    
    @POST
    @Path("/all/test")
    public Page<Post> showPageableTest() {
        final Pageable pageable = PageRequest.of(0, 10, new Sort(Sort.Direction.DESC, "id"));
        return postService.findAll(pageable);
    }

}
