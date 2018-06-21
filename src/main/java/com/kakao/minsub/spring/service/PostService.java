package com.kakao.minsub.spring.service;

import com.kakao.minsub.spring.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

/**
 * Created by kakao on 2017. 7. 14..
 */
public interface PostService {

    Post findOne(int id);

    Post findOneByProfileId(int profileId);

    Post save(Post post);

    void delete(Integer id);

    Collection<Post> findAvailablePosts();

    Page<Post> findAll(Pageable pageable);
    
    Page<Post> findAllNative(Pageable pageable);
    
    List<Post> findAll();
    
    void refreshCache(int id);

}
