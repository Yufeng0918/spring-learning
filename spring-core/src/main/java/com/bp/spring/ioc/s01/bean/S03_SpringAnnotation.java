package com.bp.spring.ioc.s01.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by day68c on 2/3/2017.
 */
public class S03_SpringAnnotation {

    public static void main(String[] args) {
        ApplicationContext factory = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        Person p1 = (Person) factory.getBean("chinese");
        System.out.println(p1.sayHello("JianGuo"));
        System.out.println(p1.sayGoodbye("JianGuo"));

        p1 = (Person) factory.getBean("american");
        System.out.println(p1.sayHello("Matt"));
        System.out.println(p1.sayGoodbye("Matt"));
    }
}
