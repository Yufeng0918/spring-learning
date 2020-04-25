package com.bp.spring.aop.proxyfactorybean;

import org.springframework.aop.ThrowsAdvice;

import java.lang.reflect.Method;

public class TransactionManager implements ThrowsAdvice {


    public void afterThrowing(Method method, Object[] args, Object target,
                              Exception ex) {
        System.out.println("method:" + method.getName());
        System.out.println("message" + ex.getMessage());
    }


    public void afterThrowing(Exception ex) {
        System.out.println("------------------");
    }
}
