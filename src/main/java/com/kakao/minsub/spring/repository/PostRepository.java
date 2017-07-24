package com.kakao.minsub.spring.repository;

import com.kakao.minsub.spring.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Created by kakao on 2017. 7. 14..
 */
public interface PostRepository extends CrudRepository<Post, Integer> {
    public Post findOneByProfileId(Integer profileId);

    @Query(value = "select * from posts where is_show=TRUE ", nativeQuery=true)
    @Transactional
    public Collection<Post> findAvailablePost();

    public Page<Post> findAll(Pageable pageable);
}
