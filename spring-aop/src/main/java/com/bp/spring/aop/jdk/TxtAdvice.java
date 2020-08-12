package com.bp.spring.aop.jdk;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

/**
 * Created by Yufeng on 1/8/2017.
 */

@Aspect
public class TxtAdvice {


    @Pointcut("execution(* com.bp.spring.aop.jdk.TxtService.save(*))")
    public void save() {
    }

    @Before(value = "save()", argNames = "jp")
    public void doBefore(JoinPoint jp) {
        Object[] args = jp.getArgs();
        System.out.println(
                "log doBefore method: " + jp.getTarget().getClass().getName() + "." + jp.getSignature().getName() + ", " +
                        Arrays.asList(args));
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

    @AfterReturning(value = "save()", returning = "result")
    public void doAfterReturning(Object result) {
        System.out.println("log after returning: " + result);
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
