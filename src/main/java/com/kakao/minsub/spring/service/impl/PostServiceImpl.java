package com.kakao.minsub.spring.service.impl;

import com.kakao.minsub.spring.model.Post;
import com.kakao.minsub.spring.repository.PostRepository;
import com.kakao.minsub.spring.service.PostService;
import com.kakao.minsub.spring.util.TimeControll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by kakao on 2017. 7. 24..
 */
@Service
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

    @Override
    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }
    
    @Override
    public Page<Post> findAllNative(Pageable pageable) {
        List<Post> posts = postRepository.findAllOrderByIdDesc(pageable.getOffset(), pageable.getPageSize());
        long totalCount = postRepository.countByIsShowIsTrue();
        return new PageImpl<>(posts, pageable, totalCount);
    }
    
    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }
    
    @Override
    public void refreshCache(int id) {
        postRepository.evictCache(id);
    }
}
