package com.bp.spring.aop.s01.proxyfactorybean;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class S01_SpringAOP {

    public static void main(String[] args) {

        AbstractApplicationContext factory = new ClassPathXmlApplicationContext("aop/s01/proxyfactorybean/applicationContext.xml");
        Person person = (Person) factory.getBean("person");
        person.study();

        Dog dog = (Dog) factory.getBean("dog");
        System.out.println(dog.getClass().getName());

        try {
            dog.remove();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            dog.save();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
