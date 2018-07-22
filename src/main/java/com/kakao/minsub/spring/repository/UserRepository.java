package com.kakao.minsub.spring.repository;

import com.kakao.minsub.spring.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by kakao on 2017. 7. 14..
 */
public interface UserRepository extends CrudRepository<User, String> {

}
