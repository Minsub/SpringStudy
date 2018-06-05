package com.kakao.minsub.spring.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestLog implements Serializable {
    private static final long serialVersionUID = -145293877700322459L;
    
    public String date;
    public String time;
    public Long ts;
    public String method;
    public String path;
    public String userAgent;
    public Integer errCd;
    public String rip;//remote ip
    public Integer status;
    public String lang;
    public Long elapsed;
    public String from;
    public String rs;
    public String rsm;
    public String body;
    public Object etc;
    public String err;
}