package com.kk.springecom.springecom.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ValidationAspect {

    public static final Logger LOGGER = LoggerFactory.getLogger(ValidationAspect.class);


    // The @Around annotation is used to create advice that wraps a method execution.
    // The pointcut expression specifies that this advice should be applied to any execution
    // of the getProductById method in the ProductService class.
    // The args(postId) part of the expression is used to bind the method's argument to the postId parameter.
    // This ensures that when the getProductById method is called, the value of its first argument
    // will be passed to the validateAndUpdate method as the postId parameter.
    // This allows us to access the method's arguments within the advice, enabling us to perform
    // additional validation or logic based on the input parameters.
    @Around("execution(* com.kk.springecom.springecom.service.ProductService.getProductById(..)) && args(postId)")
    public Object validateAndUpdate(ProceedingJoinPoint jp, int postId) throws Throwable {
        if (postId < 0) {
            LOGGER.info("Invalid negative postId: {}", postId);
            postId = -postId; //simple math to negate
            LOGGER.info("new postId: {}", postId);
        }
        // We need to pass the modified postId to the method, so we create a new array of Objects
        // and pass it to the proceed method. This will replace the original argument with the new one.
        Object proceed = jp.proceed(new Object[]{postId});
        return proceed;


    }
}