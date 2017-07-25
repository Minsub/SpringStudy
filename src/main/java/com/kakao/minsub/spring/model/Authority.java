package com.kakao.minsub.spring.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by kakao on 2017. 7. 25..
 */
@Entity
@Table(name = "authority")
public class Authority implements GrantedAuthority, Serializable {
    private static final long serialVersionUID = -3811651376856885180L;

    @EmbeddedId
    public AuthorityId authorityId;

    @Override
    public String getAuthority() {
        return "ROLE_"+authorityId.getAuthorityName().toUpperCase();
    }
}
