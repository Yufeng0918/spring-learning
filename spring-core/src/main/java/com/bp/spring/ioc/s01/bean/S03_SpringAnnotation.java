package com.bp.spring.ioc.s01.bean;

import com.bp.spring.ioc.s01.bean.config.ApplicationConfig;
import com.bp.spring.ioc.s01.bean.config.MyConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

import java.util.stream.Stream;

/**
 * Created by day68c on 2/3/2017.
 */
public class S03_SpringAnnotation {

    public static void main(String[] args) {
        ApplicationContext factory = new AnnotationConfigApplicationContext(ApplicationConfig.class, MyConfig.class);
        System.out.println("context created");
        Person p1 = (Person) factory.getBean("chinese");
        System.out.println(p1.sayHello("JianGuo"));
        System.out.println(p1.sayGoodbye("JianGuo"));

        p1 = (Person) factory.getBean("american");
        System.out.println(p1.sayHello("Matt"));
        System.out.println(p1.sayGoodbye("Matt"));

        String[] beanNames = factory.getBeanDefinitionNames();
        Stream.of(beanNames).forEach(name -> System.out.println("loaded bean: " + name));

        Object bean1 = factory.getBean("person");
        Object bean2 = factory.getBean("person");
        System.out.println(bean1 == bean2);

        Environment environment = factory.getEnvironment();
        String property = environment.getProperty("os.name");
        System.out.println("env " + environment + ", os " + property);
    }
}
