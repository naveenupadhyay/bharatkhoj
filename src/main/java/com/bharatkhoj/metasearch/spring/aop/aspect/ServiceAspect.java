package com.bharatkhoj.metasearch.spring.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class ServiceAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceAspect.class);

    @Pointcut("execution(* com.bharatkhoj.metasearch.service..**(..))")
    public void executeServicesMethods() {
    }

    @Around("executeServicesMethods()")
    public Object profile(ProceedingJoinPoint pjp) throws Throwable {
        long timeStartInMillisecs = System.currentTimeMillis();
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Object output = pjp.proceed();
        LOGGER.info("Time Taken to execute : " + signature + " " + (System.currentTimeMillis() - timeStartInMillisecs) + " ms");
        return output;
    }

}
