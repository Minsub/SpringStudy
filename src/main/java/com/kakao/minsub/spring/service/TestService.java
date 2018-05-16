package com.kakao.minsub.spring.service;

import com.kakao.minsub.spring.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

/**
 * Created by kakao on 2017. 7. 14..
 */
public interface TestService {

    int getInt(int count);
    void print(int count);
    void testJPA();
}
