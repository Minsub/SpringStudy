package com.kakao.minsub.spring.config.validator;

import com.kakao.minsub.spring.config.validator.group.First;
import com.kakao.minsub.spring.config.validator.group.Second;
import com.kakao.minsub.spring.config.validator.group.Third;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

/**
 * Created by kakao on 2017. 7. 24..
 */
@GroupSequence({Default.class, First.class, Second.class, Third.class})
public interface FullValidation {
}
