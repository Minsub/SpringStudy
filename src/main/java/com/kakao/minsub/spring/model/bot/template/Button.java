package com.kakao.minsub.spring.model.bot.template;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Button {
    public String action;
    public String label;
    public String messageText;
    public String webLinkUrl;
}
