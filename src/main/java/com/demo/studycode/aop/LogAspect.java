package com.demo.studycode.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect // AOP 사용
@Component  // Spring에서 관리하는 일반적인 Bean
@Slf4j
public class LogAspect {

    @Pointcut("execution(* com.demo.studycode.controller..*.*(..))"
            + " || execution(* com.demo.studycode.service..*Impl.*(..))"
            + " || execution(* com.demo.studycode.model..*Impl.*(..))")
    private void cut() {
    }

    @Around("cut()")
    public Object aroundLog(ProceedingJoinPoint joinPoint) throws Throwable {

        // 메서드 정보 받아오기
        Method method = getMethod(joinPoint);
        log.info("======= 메서드 정보 = {} =======", method.getName());

        // 파라미터 받아오기
        Object[] args = joinPoint.getArgs();
        if (args.length == 0) log.info("no parameter");
        log.info("==> 파라미터 타입 = {}", args.getClass().getSimpleName());
        log.info("==> 파라미터 값 = {}", args);

        for (Object arg : args) {
            if (arg == null) {
                log.info("==> 파라미터 값 = {}", "null");
            } else {
                log.info("==> 파라미터 타입 = {}", arg.getClass().getSimpleName());
                log.info("==> 파라미터 값 = {}", arg);
            }
        }

        // proceed()를 호출하여 실제 메서드 실행
        Object returnObj = joinPoint.proceed();

        // 메서드의 리턴값 로깅
        log.info("==> 리턴 타입 = {}", returnObj.getClass().getSimpleName());
        log.info("==> 리턴 값 = {}", returnObj);

        return returnObj;
    }

    private Method getMethod(ProceedingJoinPoint proceedingJoinPoint) {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        return signature.getMethod();
    }

}
