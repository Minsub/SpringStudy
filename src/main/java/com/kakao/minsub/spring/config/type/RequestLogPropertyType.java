package com.kakao.minsub.spring.config.type;

public enum RequestLogPropertyType {
    startAt("__startAt__"),
    body("__body__"),
    ;
    
    String contextValue;
    
    RequestLogPropertyType(String contextValue) {
        this.contextValue = contextValue;
    }
    
    public String getContextValue() {
        return this.contextValue;
    }
}
