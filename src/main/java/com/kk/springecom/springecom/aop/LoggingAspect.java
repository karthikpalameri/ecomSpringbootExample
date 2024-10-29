package com.kk.springecom.springecom.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    //example:
    //"execution return-type class-name.method-nam(args)"
    @Before("execution(* com.kk.springecom.springecom.service.ProductService.getAllProducts(..)) || execution(* com.kk.springecom.springecom.service.ProductService.getProductById(..))")
    public void logMethodCalll(JoinPoint jp) {
        LOGGER.info("Before called LogginAspect.logMethodCall() :" + jp.getSignature().getName() + " is called");
    }

    //example:
    //"execution return-type class-name.method-nam(args)"
    @After("execution(* com.kk.springecom.springecom.service.ProductService.getAllProducts(..)) || execution(* com.kk.springecom.springecom.service.ProductService.getProductById(..))")
    public void logMethodExecuted(JoinPoint jp) {
        LOGGER.info("After called LogginAspect.logMethodCall() :" + jp.getSignature().getName() + " is called");
    }

    //example:
    //"execution return-type class-name.method-nam(args)"
    @AfterThrowing("execution(* com.kk.springecom.springecom.service.ProductService.getAllProducts(..)) || execution(* com.kk.springecom.springecom.service.ProductService.getProductById(..))")
    public void logMethodCrash(JoinPoint jp) {
        LOGGER.info("AfterThrowing called if exception LogginAspect.logMethodCall() :" + jp.getSignature().getName() + " is called");
    }

    //example:
    //"execution return-type class-name.method-nam(args)"
    @AfterReturning("execution(* com.kk.springecom.springecom.service.ProductService.getAllProducts(..)) || execution(* com.kk.springecom.springecom.service.ProductService.getProductById(..))")
    public void logMethodExecutedSuccess(JoinPoint jp) {
        LOGGER.info("AfterReturning called if success LogginAspect.logMethodCall() :" + jp.getSignature().getName() + " is called");
    }
}