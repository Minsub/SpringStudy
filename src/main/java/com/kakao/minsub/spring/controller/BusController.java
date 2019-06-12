package com.kakao.minsub.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kakao.minsub.spring.model.bot.BotQuickReply;
import com.kakao.minsub.spring.model.bot.BotResponse;
import com.kakao.minsub.spring.model.bot.BotTemplate;
import com.kakao.minsub.spring.model.bot.template.BasicCard;
import com.kakao.minsub.spring.model.bot.template.Button;
import com.kakao.minsub.spring.sample.BeanLifeCycle;
import com.kakao.minsub.spring.service.MapService;
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

@Path("/bus")
@Api("Bus")
@Produces(MediaType.APPLICATION_JSON)
public class BusController {
    private final Logger logger = LoggerFactory.getLogger(BusController.class);
    
    @Autowired
    private MapService mapService;
    
    @POST
    @Path("/register/{type}")
    public BotResponse register(@PathParam("type") String type, Map<String, Object> body) throws Exception {
        logger.info(new ObjectMapper().writeValueAsString(body));
        
        Map<String, Object> output = mapService.register(type, body);
        return BotResponse.builder()
                .version("2.0")
                .template(BotTemplate.builder().outputs(Lists.newArrayList(output)).build())
                .build();
    }
    
    @POST
    @Path("/alarms")
    public BotResponse alarms(Map<String, Object> body) throws Exception {
        logger.info(new ObjectMapper().writeValueAsString(body));
        
        Map<String, Object> output = mapService.alarmList();
        return BotResponse.builder()
                .version("2.0")
                .template(BotTemplate.builder().outputs(Lists.newArrayList(output)).build())
                .build();
    }
    
    @POST
    @Path("/now")
    public BotResponse now(Map<String, Object> body) throws Exception {
        logger.info(new ObjectMapper().writeValueAsString(body));
        
        Map<String, Object> output = mapService.now(body);
        List<BotQuickReply> quickReplies = Lists.newArrayList(new BotQuickReply("바로등록 5분"), new BotQuickReply("바로등록 7분"), new BotQuickReply("바로등록 10분"), new BotQuickReply("바로등록 15분"));
        return BotResponse.builder()
                .version("2.0")
                .template(BotTemplate.builder().outputs(Lists.newArrayList(output)).quickReplies(quickReplies).build())
                .build();
    }
    
    @POST
    @Path("/register/quick")
    public BotResponse registerQuick(Map<String, Object> body) throws Exception {
        logger.info(new ObjectMapper().writeValueAsString(body));
    
        Map<String, Object> output = mapService.registerQuick(body);
        return BotResponse.builder()
                .version("2.0")
                .template(BotTemplate.builder().outputs(Lists.newArrayList(output)).build())
                .build();
    }
    
    @POST
    @Path("/now/test")
    public BotResponse tttttttt(Map<String, Object> body) throws Exception {
        logger.info(new ObjectMapper().writeValueAsString(body));
        
        Map<String, Object> output = Maps.newHashMap();
        BasicCard basicCard = new BasicCard();
        basicCard.title = "제목입니다.";
        basicCard.description = "설명입니다.";
        Button button = new Button();
        button.action = "webLink";
        button.label = "자세히";
        button.webLinkUrl = "http://www.daum.net";
        basicCard.buttons = Lists.newArrayList(button);
        output.put("basicCard", basicCard);
        
        return BotResponse.builder()
                .version("2.0")
                .template(BotTemplate.builder().outputs(Lists.newArrayList(output)).build())
                .build();
    }
    
    
    
    
    @POST
    @Path("/bot")
    public BotResponse bot(Map<String, Object> body) throws Exception {
        logger.info(new ObjectMapper().writeValueAsString(body));
        
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
        logger.info(new ObjectMapper().writeValueAsString(body));
        
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
        logger.info(new ObjectMapper().writeValueAsString(body));
        
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
