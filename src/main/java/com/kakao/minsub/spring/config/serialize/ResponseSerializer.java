package com.kakao.minsub.spring.config.serialize;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public abstract class ResponseSerializer<T> extends JsonSerializer<T> {
    @Override
    public void serialize(T value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        final Object serialized = serialize(value);
        if (serializers.getConfig().getDefaultPropertyInclusion().getValueInclusion() == JsonInclude.Include.NON_NULL) {
            if( serialized instanceof Map){
                ((Map)serialized).values().removeAll(Collections.singleton(null));
            }
        }
        gen.writeObject(serialized);
    }
    
    public abstract Object serialize(T value);
}
