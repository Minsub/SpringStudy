package com.kakao.minsub.spring.model;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by kakao on 2017. 7. 25..
 */
@Data
@Embeddable
public class AuthorityId implements Serializable {
    private static final long serialVersionUID = -3785546989533047681L;

    public String username;
    public String authorityName;
}

