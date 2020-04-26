package com.bp.spring.aop.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by Yufeng on 1/8/2017.
 */

@Aspect
public class TxtAdvice {


    @Pointcut("execution(* com.bp.spring.aop.annotation.TxtService.save(*))")
    public void save() { }

    @Before("save()")
    public void doBefore(JoinPoint jp) {
        System.out.println(
                "log doBefore method: " + jp.getTarget().getClass().getName() + "." + jp.getSignature().getName());
    }

    @After("save()")
    public void doAfter(JoinPoint jp) {
        System.out.println(
                "log doAfter method: " + jp.getTarget().getClass().getName() + "." + jp.getSignature().getName());
    }

    @AfterThrowing("save()")
    public void doThrowing(JoinPoint jp) {
        System.out.println(
                "log doThrowing method: " + jp.getTarget().getClass().getName() + "." + jp.getSignature().getName());
    }


//    @Around("save()")
//    public Object doProfiling(ProceedingJoinPoint pjp) throws Throwable {
//
//        Object result = null;
//        try {
//            System.out.println("before");
//            result = pjp.proceed();
//            System.out.println("after");
//        }catch (Exception e){
//            System.out.println("throwing");
//        }
//        return  result;
//    }
}
