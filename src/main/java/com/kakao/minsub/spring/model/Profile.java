package com.kakao.minsub.spring.model;

import com.kakao.minsub.spring.config.validator.group.First;
import com.kakao.minsub.spring.config.validator.group.Second;
import com.kakao.minsub.spring.config.validator.group.Third;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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


    @NotNull(message = "필수 입력 항목입니다.")
    @NotBlank(message = "{error.validation.blank}", groups = First.class)
    @Length(min = 5, max = 15, message = "{model.profiles.name.length.message}", groups = Second.class)
    @Email(groups = Third.class)
    private String name;

    private String searchId;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="profileId", insertable = false)
    private Collection<Post> posts;
}
