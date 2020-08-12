package com.bp.spring.aop.jdk;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by Yufeng on 1/8/2017.
 */
public class AOPAnnotationMain {

    public static void main(String[] args) {

        ApplicationContext factory = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        TxtService txtService = (TxtService) factory.getBean("txtService");
        txtService.save("trx-01");

    }
}
