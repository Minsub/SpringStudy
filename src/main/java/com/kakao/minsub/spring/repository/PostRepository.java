package com.kakao.minsub.spring.repository;

import com.kakao.minsub.spring.model.Post;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Created by kakao on 2017. 7. 14..
 */
@CacheConfig(cacheNames = "post")
public interface PostRepository extends CrudRepository<Post, Integer> {
    
    @Cacheable(key="#p0")
    Post findOne(Integer id);
    
    Post findOneByProfileId(Integer profileId);

    @Query(value = "select * from posts where is_show=TRUE ", nativeQuery=true)
    @Transactional
    Collection<Post> findAvailablePost();

    Page<Post> findAll(Pageable pageable);
    
    @CacheEvict(key="#p0")
    default void evictCache(Integer id) {
        
    }
}
