package com.kakao.minsub.spring.model;

import com.kakao.minsub.spring.config.validator.group.First;
import com.kakao.minsub.spring.config.validator.group.Second;
import com.kakao.minsub.spring.config.validator.group.Third;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;


@Data
@NoArgsConstructor
@Entity
@Table(name = "profiles")
public class Profile implements Serializable {
    private static final long serialVersionUID = -1607588153541394505L;

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull(message = "필수 입력 항목입니다.")
    @NotBlank(message = "{validator.constraints.Profile.name.blank}", groups = First.class)
    @Length(min = 5, max = 255, message = "{validator.constraints.Profile.name.length}", groups = Second.class)
    @Email(groups = Third.class)
    private String name;
    
    @NotNull(message = "필수 입력 항목입니다.")
    @Pattern(
        regexp = "^[0-9a-z가-힣\\-_]+$", // 한글/영문 소문자/숫자/-_
        message = "한글, 영문 소문자, 숫자, 특수문자(-_)만 사용가능합니다.(띄어쓰기 불가)",
        groups = { First.class }
    )
    private String searchId;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="profileId", insertable = false,
            foreignKey = @ForeignKey(name="none",value = ConstraintMode.NO_CONSTRAINT))
    @Transient
    private Collection<Post> posts;
    
    @Builder
    public Profile(String name, String searchId) {
        this.name = name;
        this.searchId = searchId;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
