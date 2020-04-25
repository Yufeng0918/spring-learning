package com.bp.spring.ioc.entities;

import lombok.AllArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

@AllArgsConstructor
@ToString
public class Chinese implements Person {

    @Value("SG")
    private String name;

    @Value("#{20 + 5}")
    private int age;

    @Value("${person.nickName}")
    private String nickName;

    public Chinese() {
        System.out.println("chinese constructor");
    }

    @Override
    public String sayGoodbye(String name) {
        return name + " Goodbye";
    }

    @Override
    public String sayHello(String name) {
        return name + " Hello";
    }

}
