package com.kakao.minsub.spring.config.serialize;

import java.util.Map;

public abstract class ResponseMapSerializer<T> extends ResponseSerializer<T> {
    
    @Override
    public abstract Map<String, Object> serialize(T value);
}
