package com.kakao.minsub.spring.service;

import com.kakao.minsub.spring.model.Post;

import java.util.Collection;

/**
 * Created by kakao on 2017. 7. 14..
 */
public interface PostService {

    public Post findOne(int id);

    public Post findOneByProfileId(int profileId);

    public Post save(Post post);

    public void delete(Integer id);

    public Collection<Post> findAvailablePosts();
}
