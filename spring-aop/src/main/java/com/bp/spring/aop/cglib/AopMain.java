package com.bp.spring.aop.cglib;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AopMain {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AopConfig.class);
        MathCalculator mathCalculator = applicationContext.getBean(MathCalculator.class);
        mathCalculator.div(1, 1);
        applicationContext.close();
    }
}
