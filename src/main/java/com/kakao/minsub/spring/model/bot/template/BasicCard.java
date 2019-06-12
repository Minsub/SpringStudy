package com.kakao.minsub.spring.model.bot.template;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BasicCard {
    public String title;
    public String description;
    public Thumbnail thumbnail;
    public List<Button> buttons;
    
}
