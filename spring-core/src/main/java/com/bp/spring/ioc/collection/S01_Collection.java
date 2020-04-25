package com.bp.spring.ioc.collection;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class S01_Collection {

    public static void main(String[] args) {
        AbstractApplicationContext factory = new ClassPathXmlApplicationContext("ioc/s03/collection/applicationContext.xml");
        Person p = (Person) factory.getBean("chinese");
        p.test();
    }
}
