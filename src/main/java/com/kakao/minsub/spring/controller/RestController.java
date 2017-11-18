package com.kakao.minsub.spring.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.net.URLEncoder;
import java.util.Map;


@EnableCaching
@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
public class RestController {

    private final Logger logger = LoggerFactory.getLogger(RestController.class);


    @GET
    @Path("/search")
    public Map<String, Object> show(@QueryParam("query") final String query) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://dapi.kakao.com/v2/local/search/keyword.json?query=" + URLEncoder.encode(query, "UTF-8");
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "KakaoAK 5e4d6077b3f89b6ed48397b0c0bce7eb");
            headers.set("Referer", "http://jiminsub.iptime.org:8080/page/index");
            headers.set("Origin", "http://jiminsub.iptime.org:8080");
            headers.setAccessControlAllowOrigin("http://jiminsub.iptime.org:8080");

            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET,entity, Map.class);
            return response.getBody();
        } catch (Exception e) {
            logger.error("error", e);
        }
        return null;
    }
}
