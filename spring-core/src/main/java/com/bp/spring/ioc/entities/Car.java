package com.bp.spring.ioc.entities;

import org.springframework.stereotype.Component;

/**
 * @Auther: daiyu
 * @Date: 25/4/20 14:24
 * @Description:
 */
@Component
public class Car {

    public Car() {
        System.out.println("car constructor");
    }

    public void init() {
        System.out.println("init");
    }

    public void destory() {
        System.out.println("destory");
    }
}
