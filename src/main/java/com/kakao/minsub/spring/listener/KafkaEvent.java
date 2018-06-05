package com.kakao.minsub.spring.listener;

public interface KafkaEvent<T> {
    T getTarget();
    String getTargetType();
}
