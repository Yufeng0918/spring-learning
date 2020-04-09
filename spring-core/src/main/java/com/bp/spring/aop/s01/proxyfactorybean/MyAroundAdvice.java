package com.bp.spring.aop.s01.proxyfactorybean;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class MyAroundAdvice implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation arg0) throws Throwable {

        System.out.println("MyAroundAdvice" + arg0);
        Object object = arg0.proceed();
        System.out.println("MyAroundAdvice end");
        return object;
    }

}
