package com.kakao.minsub.spring;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Properties;

public class SampleCodeTest extends BaseSpringTest {
    @Autowired
    @Qualifier(value = "testProps")
    private Properties testProps;



    @Test
    public void test() {
        testProps.stringPropertyNames().forEach(s -> System.err.println(s + ": " + testProps.getProperty(s)));
    }

}
