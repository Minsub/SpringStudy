package com.kakao.minsub.spring.model.bot;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

@ToString
@Getter
@NoArgsConstructor
public class BotQuickReply {
    String action = "message"; // block, message
    String label;
    String message;
    Map data;
    
    public BotQuickReply(String message) {
        this.label = message;
        this.message = message;
    }
    
    public BotQuickReply(String label, String message) {
        this.label = label;
        this.message = message;
    }
}
