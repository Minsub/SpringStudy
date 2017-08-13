package com.kakao.minsub.spring.rest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

//@RunWith(SpringRunner.class)
//@SpringBootTest
//@WebAppConfiguration
//@EnableAutoConfiguration
public class RestTemplateTest {

    private RestTemplate restTemplate;
    
    @Before
    public void before() {
        restTemplate =  new RestTemplate();
        
    }
    
    @Test
    public void test() {
        try {
            String url = "http://rocket-api.devel.kakao.com/talk/profiles/56229/contact";
            ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);
    
    
            System.err.println(result.getStatusCode());
    
            System.err.println(result.getBody());
        } catch (HttpClientErrorException hce) {
            System.err.println(hce.getStatusCode() + ": " + hce.getStatusText());
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
        
    }

}
