package com.kakao.minsub.spring.intercepter;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

@Component
public class MethodServiceAdvisor extends AbstractPointcutAdvisor  {
    
    @Autowired
    private MethodServiceInterceptor interceptor;
    
    private final StaticMethodMatcherPointcut pointcut = new StaticMethodMatcherPointcut() {
        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            return targetClass.isAnnotationPresent(Service.class);
        }
    };
    
    
    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }
    
    @Override
    public Advice getAdvice() {
        return this.interceptor;
    }
}
