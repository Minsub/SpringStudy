package com.kakao.minsub.spring.repository;

import com.kakao.minsub.spring.model.Point;
import org.springframework.data.repository.CrudRepository;

public interface PointRedisRepository extends CrudRepository<Point, String> {
}
