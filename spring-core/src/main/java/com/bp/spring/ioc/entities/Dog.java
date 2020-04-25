package com.bp.spring.ioc.entities;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @Auther: daiyu
 * @Date: 25/4/20 14:57
 * @Description:
 */
@Component
public class Dog implements ApplicationContextAware {

    public Dog() {
        System.out.println("dog constructor");
    }

    @PostConstruct
    public void init() {
        System.out.println("dog init");
    }

    @PreDestroy
    public void destory() {
        System.out.println("dog destory");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println(applicationContext.getApplicationName());
    }
}
