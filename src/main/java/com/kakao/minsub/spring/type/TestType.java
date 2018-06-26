package com.kakao.minsub.spring.type;

public enum TestType {
    a1("/api/a1"),
    a2("/api/a2"),
    ;
    
    String url;
    
    TestType(String url) {
        this.url = url;
    }
    
    public String getUrl() {
        return this.url;
    }
}
