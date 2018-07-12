package com.kakao.minsub.spring.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by kakao on 2017. 7. 25..
 */
@Entity
@Table(name = "users")
public class User implements UserDetails, Serializable {
    private static final long serialVersionUID = -8574267236777875488L;

    @Id
    public String username;

    public String password;

    public String name;

    public boolean expired_flag;

    public boolean locked_flag;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="username", insertable = false,
            foreignKey = @ForeignKey(name="none",value = ConstraintMode.NO_CONSTRAINT))
    public Collection<Authority> authorities;

    @Transient
    public String accessKey;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return expired_flag;
    }

    @Override
    public boolean isAccountNonLocked() {
        return locked_flag;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return expired_flag;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "un: "+username + ", pw: " + password + ", name: " + name + ", exp_flag: " + expired_flag + ", lock_flag: " + locked_flag + "\n" + authorities;
//        return "un: "+username + ", pw: " + password + ", name: " + name + ", exp_flag: " + expired_flag + ", lock_flag: " + locked_flag ;
    }
}
