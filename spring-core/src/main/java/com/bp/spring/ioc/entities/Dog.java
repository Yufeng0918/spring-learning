package com.bp.spring.ioc.entities;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @Auther: daiyu
 * @Date: 25/4/20 14:57
 * @Description:
 */
@Component
public class Dog {

    public Dog() {
        System.out.println("dog constructor");
    }

    @PostConstruct
    public void init() {
        System.out.println("dog init");
    }

    @PreDestroy
    public void destory(){
        System.out.println("dog destory");
    }

}
