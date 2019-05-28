package com.kakao.minsub.spring;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

/**
 * Created by kakao on 2017. 8. 9..
 */
public class JustTest {
    
    @Test
    public void test22() throws Exception {
        String s = "12:12:34";
        LocalTime time = LocalTime.parse(s, DateTimeFormatter.ofPattern("HH:mm:ss"));
    
    
        System.out.println(LocalTime.now().isAfter(time));
        System.out.println("\uD83D\uDE80");
    }
    
    
    public void print(int a) {
        System.out.println("print: " + a);
    }
    
    public static class Pro {
        int a;
        String b;
        long c;
    }
}
