package com.bp.spring.aop.s01.proxyfactorybean;

import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

public class MyAfterReturningAdvice implements AfterReturningAdvice {
    @Override
    public void afterReturning(Object arg0, Method arg1, Object[] arg2,
                               Object arg3) throws Throwable {
        System.out.println("MyAfterReturningAdvice");
        System.out.println("arg0" + arg0);
        System.out.println("arg1" + arg1);
        System.out.println("arg2" + arg2);
        System.out.println("arg3" + arg3);
        System.out.println();
    }

}
