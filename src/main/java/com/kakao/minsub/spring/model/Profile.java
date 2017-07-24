package com.kakao.minsub.spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;


@Data
@Entity
@Table(name = "profiles")
public class Profile implements Serializable {
    private static final long serialVersionUID = -1607588153541394505L;

    @Id
    @GeneratedValue
    private Integer id;


    private String name;

    private String searchId;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="profileId", insertable = false)
    private Collection<Post> posts;
}
