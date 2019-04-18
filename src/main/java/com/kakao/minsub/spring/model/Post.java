package com.kakao.minsub.spring.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Getter
@Setter
@Entity
@Table(name = "posts")
public class Post extends BaseTimeEntity implements Serializable {
    private static final long serialVersionUID = 4143615482838587876L;

    @Id
    @GeneratedValue
    private Integer id;

    private Integer profileId;

    private String name;

    private boolean isShow;
}
