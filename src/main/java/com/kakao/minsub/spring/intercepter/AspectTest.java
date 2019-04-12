package com.kakao.minsub.spring.intercepter;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class AspectTest {
    /*
    ## advice
    @Before (이전)
      어드바이스 타겟 메소드가 호출되기 전에 어드바이스 기능을 수행
    @After (이후)
      타겟 메소드의 결과에 관계없이(즉 성공, 예외 관계없이) 타겟 메소드가 완료 되면 어드바이스 기능을 수행
    @AfterReturning (정상적 반환 이후)
      타겟 메소드가 성공적으로 결과값을 반환 후에 어드바이스 기능을 수행
    @AfterThrowing (예외 발생 이후)
      타겟 메소드가 수행 중 예외를 던지게 되면 어드바이스 기능을 수행
    @Around (메소드 실행 전후)
      어드바이스가 타겟 메소드를 감싸서 타겟 메소드 호출전과 후에 어드바이스 기능을 수행
    
    ## pointcut 지정자
    args()
    메소드의 인자가 타겟 명세에 포함된 타입일 경우
    ex) args(java.io.Serializable) : 하나의 파라미터를 갖고, 그 인자가 Serializable 타입인 모든 메소드
    @args()
    메소드의 인자가 타겟 명세에 포함된 어노테이션 타입을 갖는 경우
    ex) @args(com.blogcode.session.User) : 하나의 파라미터를 갖고, 그 인자의 타입이 @User 어노테이션을 갖는 모든 메소드 (@User User user 같이 인자 선언된 메소드)
    execution()
    접근제한자, 리턴타입, 인자타입, 클래스/인터페이스, 메소드명, 파라미터타입, 예외타입 등을 전부 조합가능한 가장 세심한 지정자
    이전 예제와 같이 풀패키지에 메소드명까지 직접 지정할 수도 있으며, 아래와 같이 특정 타입내의 모든 메소드를 지정할 수도 있다.
    ex) execution(* com.blogcode.service.AccountService.*(..) : AccountService 인터페이스의 모든 메소드
    within()
    execution 지정자에서 클래스/인터페이스까지만 적용된 경우
    즉, 클래스 혹은 인터페이스 단위까지만 범위 지정이 가능하다.
    ex) within(com.blogcode.service.*) : service 패키지 아래의 클래스와 인터페이스가 가진 모든 메소드
    ex) within(com.blogcode.service..) : service 아래의 모든 *하위패키지까지** 포함한 클래스와 인터페이스가 가진 메소드
    @within()
    주어진 어노테이션을 사용하는 타입으로 선언된 메소드
    this()
    타겟 메소드가 지정된 빈 타입의 인스턴스인 경우
    target()
    this와 유사하지만 빈 타입이 아닌 타입의 인스턴스인 경우
    @target()
    타겟 메소드를 실행하는 객체의 클래스가 타겟 명세에 지정된 타입의 어노테이션이 있는 경우
    @annotation
    타겟 메소드에 특정 어노테이션이 지정된 경우
    ex) @annotation(org.springframework.transaction.annotation.Transactional) : Transactional 어노테이션이 지정된 메소드 전부
    */
    @Around("execution(* com.kakao.minsub.spring.service.*.*(..))")
    public Object calculatePerformanceTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        final long start = System.currentTimeMillis();
        try {
            return proceedingJoinPoint.proceed();
        } finally {
            final long elapsed = System.currentTimeMillis() - start;
            log.info("AspectTest Method: {}, elapsed: {}", proceedingJoinPoint.toString(), elapsed);
        }
    }
}
