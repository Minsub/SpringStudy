package com.kakao.minsub.spring.repository;

import com.kakao.minsub.spring.model.Authority;
import com.kakao.minsub.spring.model.AuthorityId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Created by kakao on 2017. 7. 25..
 */
public interface AuthorityRepository extends CrudRepository<Authority, AuthorityId> {
    @Query(value = "select * from authority where username=?1", nativeQuery=true)
    @Transactional
    Collection<Authority> findAllByUsername(String username);
}
