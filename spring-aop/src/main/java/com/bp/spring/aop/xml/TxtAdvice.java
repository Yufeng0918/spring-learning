package com.bp.spring.aop.xml;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * Created by Yufeng on 1/8/2017.
 */
public class TxtAdvice {

    public void doAfter(JoinPoint jp) {
        System.out.println(
                "log doAfter method: " + jp.getTarget().getClass().getName() + "." + jp.getSignature().getName());
    }

    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long time = System.currentTimeMillis();
        System.out.println("process doAround start: " + time + " ms");
        Object retVal = pjp.proceed();
        time = System.currentTimeMillis() - time;
        System.out.println("process doAround end: " + time + " ms");
        return retVal;
    }

    public void doBefore(JoinPoint jp) {
        System.out.println(
                "log doBefore method: " + jp.getTarget().getClass().getName() + "." + jp.getSignature().getName());
    }

    public void doThrowing(JoinPoint jp, Throwable ex) {
        System.out.println(
                "log doThrowing method " + jp.getTarget().getClass().getName() + "." + jp.getSignature().getName() +
                " throw exception");
        System.out.println(ex.getMessage());
    }
}
