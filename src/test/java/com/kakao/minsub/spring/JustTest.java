package com.kakao.minsub.spring;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

/**
 * Created by kakao on 2017. 8. 9..
 */
public class JustTest {
    
    @Test
    public void test() {
        System.out.println("Test-1");
        
        String a = "TestString";
        a = null;
        
        System.out.println(Optional.ofNullable(a).orElse("empty"));
        
        
        
        
        List<String> list = null;
        
        List<String> list2 = Optional.ofNullable(list).orElseGet(Lists::newArrayList);
    
        System.out.println(list2.size());
    }
}
