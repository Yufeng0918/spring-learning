package com.bp.spring.autoconfig.domain;

import org.springframework.stereotype.Component;

@Component
public class Car {

    public Car(){
        System.out.println("car constructor...");
    }
}
