package com.kakao.minsub.spring.model.bot;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BotResponse {
    String version;
    BotTemplate template;
    BotContext context;
    BotData data;
    
    @Builder
    public BotResponse(String version, BotTemplate template, BotContext context, BotData data) {
        this.version = version;
        this.template = template;
        this.context = context;
        this.data = data;
    }
}
