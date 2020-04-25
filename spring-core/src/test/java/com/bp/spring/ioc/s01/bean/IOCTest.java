package com.bp.spring.ioc.s01.bean;

import com.bp.spring.ioc.s01.bean.config.ApplicationConfig;
import com.bp.spring.ioc.s01.bean.config.MyConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.beans.Transient;
import java.util.stream.Stream;

/**
 * @Auther: daiyu
 * @Date: 25/4/20 09:08
 * @Description:
 */
public class IOCTest {

    AnnotationConfigApplicationContext applicationContext =
            new AnnotationConfigApplicationContext(ApplicationConfig.class,
                                                   MyConfig.class);

    @Test
    public void testImport() {

        printBeans();

        Object object = applicationContext.getBean("colorFactoryBean");
        Object factoryBean = applicationContext.getBean("&colorFactoryBean");
        System.out.println("bean name: " + object.getClass());
        System.out.println("factory bean: " + factoryBean.getClass());
    }

    private void printBeans() {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        Stream.of(beanNames).forEach(b -> System.out.println("bean name: " + b));
//        System.out.println("bean name:" + beanName);
    }
}
