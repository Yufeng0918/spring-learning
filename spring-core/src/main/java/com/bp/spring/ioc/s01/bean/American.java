package com.bp.spring.ioc.s01.bean;

public class American implements Person {

    @Override
    public String sayGoodbye(String name) {
        return name + " hello";
    }

    @Override
    public String sayHello(String name) {
        return name + " goodbye";
    }

}
