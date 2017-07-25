package com.kakao.minsub.spring.repository;

import com.kakao.minsub.spring.model.Post;
import com.kakao.minsub.spring.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Created by kakao on 2017. 7. 14..
 */
public interface UserRepository extends CrudRepository<User, String> {

}
