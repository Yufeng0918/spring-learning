package com.bp.spring.ioc.s01.bean;

public class Chinese implements Person {

    @Override
    public String sayGoodbye(String name) {
        return name + " Goodbye";
    }

    @Override
    public String sayHello(String name) {
        return name + " Hello";
    }

}
