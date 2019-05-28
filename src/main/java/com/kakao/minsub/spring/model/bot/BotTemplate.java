package com.kakao.minsub.spring.model.bot;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@ToString
@NoArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BotTemplate {
    List<Map> outputs;
    List<BotQuickReply> quickReplies;
    
    @Builder
    public BotTemplate(List<Map> outputs, List<BotQuickReply> quickReplies) {
        this.outputs = outputs;
        this.quickReplies = quickReplies;
    }
}
