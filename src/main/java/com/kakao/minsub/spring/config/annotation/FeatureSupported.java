package com.kakao.minsub.spring.config.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface FeatureSupported {
    enum FeatureType { manager, normal, undefined }
    FeatureType value() default FeatureType.undefined;
}
