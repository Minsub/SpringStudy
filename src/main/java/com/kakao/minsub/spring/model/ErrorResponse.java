package com.kakao.minsub.spring.model;

import java.io.Serializable;

/**
 * Created by kakao on 2017. 7. 24..
 */
public class ErrorResponse implements Serializable {
    private static final long serialVersionUID = -7856162837920907282L;

    public String code;
    public String entityName;
    public String columnName;
    public String message;
}
