package com.kakao.minsub.spring.intercepter;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class MethodServiceInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        final long start = System.currentTimeMillis();
    
        try {
            return invocation.proceed();
        } finally {
            final long elapsed = System.currentTimeMillis() - start;
            log.info("Intercepter Method: {}, elapsed: {}", invocation.getMethod().getName(), elapsed);
        }
    }
}
