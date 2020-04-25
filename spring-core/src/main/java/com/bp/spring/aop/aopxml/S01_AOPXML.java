package com.bp.spring.aop.aopxml;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Yufeng on 1/8/2017.
 */
public class S01_AOPXML {

    public static void main(String[] args) {

        AbstractApplicationContext factory =
                new ClassPathXmlApplicationContext("aop/s02/aopxml/applicationContext.xml");
        TxtService txtService = (TxtService) factory.getBean("txtService");
        txtService.save("trx-01");

    }
}
