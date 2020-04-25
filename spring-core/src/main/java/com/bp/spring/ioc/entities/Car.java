package com.bp.spring.ioc.entities;

import lombok.NoArgsConstructor;

/**
 * @Auther: daiyu
 * @Date: 25/4/20 14:24
 * @Description:
 */
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
