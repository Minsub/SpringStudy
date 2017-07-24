package com.kakao.minsub.spring.service.impl;

import com.kakao.minsub.spring.model.Post;
import com.kakao.minsub.spring.repository.PostRepository;
import com.kakao.minsub.spring.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Created by kakao on 2017. 7. 24..
 */
@Component
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public Post findOne(int id) {
        return postRepository.findOne(id);
    }

    @Override
    public Post findOneByProfileId(int profileId) {
        return postRepository.findOneByProfileId(profileId);
    }

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public void delete(Integer id) {
        postRepository.delete(id);
    }

    @Override
    public Collection<Post> findAvailablePosts() {
        return postRepository.findAvailablePost();
    }
}
