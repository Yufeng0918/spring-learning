package com.bp.spring.aop.s01.proxyfactorybean;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class MyMethodBeforeAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method arg0, Object[] arg1, Object arg2)
            throws Throwable {
        System.out.println("MyMethodBeforeAdvice");
        System.out.println("arg0" + arg0);
        System.out.println("arg1" + arg1);
        System.out.println("arg2" + arg2);
        System.out.println();
    }

}
