package com.kk.springecom.springecom.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PerformanceMonitorAspect {
    public static final Logger LOGGER = LoggerFactory.getLogger(PerformanceMonitorAspect.class);

    @Around("execution(* com.kk.springecom.springecom.service.ProductService.*(..))")
    public Object monitorTime(ProceedingJoinPoint jp) throws Throwable {//remember to return Object or it won't work

        long startTime = System.currentTimeMillis();
        Object proceed = jp.proceed();//execute the method
        long endTime = System.currentTimeMillis();

        LOGGER.info("Total time taken by " + jp.getSignature().getName() + " : " + (endTime - startTime) + " ms");

        return proceed;//return the produced object
    }
}