package com.kakao.minsub.spring.repository;

import com.kakao.minsub.spring.model.Post;
import com.kakao.minsub.spring.model.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Created by kakao on 2017. 7. 14..
 */
public interface ProfileRepository extends CrudRepository<Profile, Integer> {

}
