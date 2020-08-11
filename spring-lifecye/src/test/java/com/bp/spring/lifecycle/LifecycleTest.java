package com.bp.spring.lifecycle;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class LifecycleTest {

    @Test
    public void testLifecyel(){
        //1、创建ioc容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(LifecyecleConfig.class);
        System.out.println("=====================> 容器创建完成...");
        applicationContext.close();
    }

}
