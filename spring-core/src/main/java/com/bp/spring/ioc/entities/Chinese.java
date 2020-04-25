package com.bp.spring.ioc.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
public class Chinese implements Person {

    private String name;

    private int age;

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
