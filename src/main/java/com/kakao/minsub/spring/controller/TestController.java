package com.kakao.minsub.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kakao.minsub.spring.model.bot.BotContext;
import com.kakao.minsub.spring.model.bot.BotQuickReply;
import com.kakao.minsub.spring.model.bot.BotResponse;
import com.kakao.minsub.spring.model.bot.BotTemplate;
import com.kakao.minsub.spring.sample.BeanLifeCycle;
import com.kakao.minsub.spring.service.TestService;
import com.kakao.minsub.spring.type.TestType;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Path("/test")
@Api("Test")
@Produces(MediaType.APPLICATION_JSON)
public class TestController {
    private final Logger logger = LoggerFactory.getLogger(TestController.class);
    
    @Autowired
    private TestService testService;
    
    @Autowired
    private BeanLifeCycle beanLifeCycle;
    
    @GET
    @Path("/jpa")
    public Map jpa() {
        testService.testJPA();
        return Collections.emptyMap();
    }
    
    @GET
    @Path("/version")
    public Map version() {
        Map<String, Object> result = Maps.newHashMap();
        result.put("version", 1);
    
        beanLifeCycle.print();
        
        return result;
    }
    
    @GET
    @Path("/empty")
    public void empty() {
    
    }
    
    @GET
    @Path("/gracefull")
    public void gracefull() throws Exception {
        logger.info("START graceful test");
    
        TimeUnit.SECONDS.sleep(5);
        
        logger.info("END graceful test");
    }
    
    @GET
    @Path("/pathparm/{type}")
    public void gracefull(@PathParam("type") TestType type) throws Exception {
        logger.info("type: {}", type);
    }
    
    @GET
    @Path("/greeting")
    public String greeting() throws Exception {
        return testService.getGreeting();
    }
    
    
    @POST
    @Path("/webhook")
    public void webhook(Map<String, Object> body) throws Exception {
        logger.info(body == null ? "NULL!!!" : new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(body));
    }
    
    @POST
    @Path("/bot")
    public BotResponse bot(Map<String, Object> body) throws Exception {
        logger.info(body.toString());
        
        Map<String, Object> output = Maps.newHashMap();
        Map<String, Object> outputBody = Maps.newHashMap();
        outputBody.put("text", "간단하게 텍스트!!");
        output.put("simpleText", outputBody);
        
        return BotResponse.builder()
                .version("2.0")
                .template(BotTemplate.builder().outputs(Lists.newArrayList(output)).build())
                .build();
    }
    
    @POST
    @Path("/bot/replies")
    public BotResponse botWithReplies(Map<String, Object> body) throws Exception {
        logger.info(body.toString());
        
        Map<String, Object> output = Maps.newHashMap();
        Map<String, Object> outputBody = Maps.newHashMap();
        outputBody.put("text", "주문이 완료되었습니다.");
        output.put("simpleText", outputBody);
        
        List<BotQuickReply> quickReplies = Lists.newArrayList(new BotQuickReply("라이언"), new BotQuickReply("무지"), new BotQuickReply("해피"));
        
        return BotResponse.builder()
                .version("2.0")
                .template(BotTemplate.builder().outputs(Lists.newArrayList(output)).quickReplies(quickReplies).build())
                .build();
    }
    
    @POST
    @Path("/bot/check")
    public Map check(Map<String, Object> body) throws Exception {
        logger.info(body.toString());
        
        Map<String, Object> map = Maps.newHashMap();
        map.put("status", "SUCCESS");
        map.put("value", "뭔말인지모르것다");
        
        Map<String, Object> value = (Map)body.get("value");
        String utterance = body.get("utterance").toString();
    
        if (!StringUtils.isNumeric(utterance)) {
            map.put("status", "FAIL");
        }
        return map;
    }
}
