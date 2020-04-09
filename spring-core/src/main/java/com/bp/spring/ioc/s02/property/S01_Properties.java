package com.bp.spring.ioc.s02.property;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class S01_Properties {

    public static void main(String[] args) {

        AbstractApplicationContext factory = new ClassPathXmlApplicationContext("ioc/s02/property/applicationContext.xml");
        Person p = (Person) factory.getBean("chinese");
        p.work();
    }
}
