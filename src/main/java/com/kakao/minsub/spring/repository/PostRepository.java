package com.kakao.minsub.spring.repository;

import com.kakao.minsub.spring.model.Post;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * Created by kakao on 2017. 7. 14..
 */
@CacheConfig(cacheNames = "post")
public interface PostRepository extends CrudRepository<Post, Integer> {
    
    @Cacheable(key="#p0")
    Post findOneByid(Integer id);
    
    Post findOneByProfileId(Integer profileId);

    @Query(value = "select * from posts USE INDEX(idx_profile_id) where is_show=TRUE ", nativeQuery=true)
    @Transactional
    Collection<Post> findAvailablePost();

    Page<Post> findAll(Pageable pageable);
    
    Page<Post> findAllByProfileId(int profileId, Pageable pageable);
    
    @Query(value = "select * from posts as p USE INDEX(idx_profile_id) where is_show=TRUE ORDER BY ID DESC LIMIT :offset, :size", nativeQuery=true)
    List<Post> findAllOrderByIdDesc(@Param("offset") long offset, @Param("size") int size);
    
    long countByIsShowIsTrue();
    
    @Cacheable(key="'all'")
    List<Post> findAll();
    
    @CacheEvict(key="#p0")
    default void evictCache(Integer id) {
        
    }
}
