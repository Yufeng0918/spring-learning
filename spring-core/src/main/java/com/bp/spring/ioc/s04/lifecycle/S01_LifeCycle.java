package com.bp.spring.ioc.s04.lifecycle;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class S01_LifeCycle {

    public static void main(String[] args) {

        AbstractApplicationContext factory = new ClassPathXmlApplicationContext("ioc/s04/lifecycle/applicationContext.xml");
        Person p = (Person) factory.getBean("chinese");
        p.work();
        SpringBean springBean1 = (SpringBean) factory.getBean("springBean");
        System.out.println(springBean1);
        factory.destroy();


        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        SpringBean springBean2 = (SpringBean) applicationContext.getBean("springBean");
        System.out.println(springBean2);
        ((AnnotationConfigApplicationContext) applicationContext).close();
    }
}
