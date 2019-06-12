package com.kakao.minsub.spring.model.bot.template;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Thumbnail {
    public String imageUrl;
    
    @Builder
    public Thumbnail(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
