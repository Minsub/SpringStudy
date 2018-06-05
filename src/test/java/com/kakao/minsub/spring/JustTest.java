package com.kakao.minsub.spring;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by kakao on 2017. 8. 9..
 */
public class JustTest {
    
    @Test
    public void test22() throws Exception {
        Map<String, Object> input = Maps.newHashMap();
        input.put("appleBanana", 1);
        input.put("addressName", "Hello");
    
        ObjectMapper mapper = new ObjectMapper()
//                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
//                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
//                .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
                ;
        
        String output = mapper.writeValueAsString(input);
        System.out.println(output);
    
        System.out.println(mapper.readValue(output, Map.class));
        
        
        String s = "kafka";
        System.out.println(Integer.valueOf(s));
    }
}
