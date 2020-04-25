package com.bp.spring.ioc.s01.bean;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class Chinese implements Person {

    private String name;

    private int age;

    @Override
    public String sayGoodbye(String name) {
        return name + " Goodbye";
    }

    @Override
    public String sayHello(String name) {
        return name + " Hello";
    }

}
