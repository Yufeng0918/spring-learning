package com.bp.spring.ioc.scope;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by day68c on 9/3/2017.
 */
public class S01_Scope {

    public static void main(String[] args) {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        SingletonBean singletonBean1 = (SingletonBean) applicationContext.getBean("singletonBean");
        SingletonBean singletonBean2 = (SingletonBean) applicationContext.getBean("singletonBean");
        System.out.println(singletonBean1 == singletonBean2);

        PrototypeBean prototypeBean1 = (PrototypeBean) applicationContext.getBean("prototypeBean");
        PrototypeBean prototypeBean2 = (PrototypeBean) applicationContext.getBean("prototypeBean");
        System.out.println(prototypeBean1 == prototypeBean2);

    }
}
