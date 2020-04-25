package com.bp.spring.ioc;

import com.bp.spring.ioc.entities.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class S02_SpringIOC {

    public static void main(String[] args) {

        ApplicationContext factory = new ClassPathXmlApplicationContext("ioc/s01/bean/applicationContext.xml");

        Person p1 = (Person) factory.getBean("chinese");
        System.out.println(p1.sayHello("Zhangsan"));
        System.out.println(p1.sayGoodbye("Zhangsan"));

        p1 = (Person) factory.getBean("american");
        System.out.println(p1.sayHello("Jack"));
        System.out.println(p1.sayGoodbye("Jack"));
    }
}
