package com.kakao.minsub.spring.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Data
@Entity
@Table(name = "posts")
public class Post implements Serializable {
    private static final long serialVersionUID = 4143615401615587132L;

    @Id
    @GeneratedValue
    private Integer id;

    private Integer profileId;

    private String name;

    private boolean isShow;
}
