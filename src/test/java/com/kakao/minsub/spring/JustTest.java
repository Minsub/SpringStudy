package com.kakao.minsub.spring;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by kakao on 2017. 8. 9..
 */
public class JustTest {
    
    @Test
    public void test() {
        
        Date c1 = new Date();
        Date c2 = DateUtils.addDays(c1, -15);
        System.out.println("c1: " + c1);
        System.out.println("c2: " + c2);
        System.out.println(c1.compareTo(c2) > 0 ? c1 : c2);
    
        System.out.println(c2.before(DateUtils.addDays(new Date(), -14)));
    
        
                
    }
}
