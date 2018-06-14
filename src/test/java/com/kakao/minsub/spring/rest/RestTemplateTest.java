package com.kakao.minsub.spring.rest;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Map;


public class RestTemplateTest {

    private RestTemplate restTemplate;
    
    @Before
    public void before() {
        restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(Lists.newArrayList(new FormHttpMessageConverter()));
        
    }
    
    @Test
    public void restTest() throws Exception {
        String appkey = "e30672001c218b48ad05ca279569f1fb";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>(4);
        params.add("key", appkey);
    
        try {
            String url = "http://....";
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.AUTHORIZATION, "value....");
//            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.setContentType(new MediaType("application", "x-www-form-urlencoded", Charset.forName("UTF-8")));
    
            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
            
            ResponseEntity<Map> result = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
//            ResponseEntity<Map> result = restTemplate.postForEntity("http://sandbox-kapi.kakao.com/v1/internal/app/checkquota", entity, Map.class);
            System.out.println(result.getBody());
        } catch (HttpClientErrorException e) {
            System.err.println(e.getStatusCode());
            System.err.println(e.getResponseBodyAsString());
            throw e;
        }
    
        System.out.println("FINISH");
       
    }

}
