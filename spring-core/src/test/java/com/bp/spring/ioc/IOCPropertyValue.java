package com.bp.spring.ioc;

import com.bp.spring.ioc.config.MyConfigOfPropertyValue;
import com.bp.spring.ioc.entities.Chinese;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.stream.Stream;

/**
 * @Auther: daiyu
 * @Date: 25/4/20 09:08
 * @Description:
 */
public class IOCPropertyValue {

    AnnotationConfigApplicationContext applicationContext =
            new AnnotationConfigApplicationContext(MyConfigOfPropertyValue.class);

    @Test
    public void testInject() {

        Chinese person = (Chinese) applicationContext.getBean("person");
        System.out.println(person);

        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String property = environment.getProperty("person.nickName");
        System.out.println(property);
        printBeans();
    }

    private void printBeans() {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        Stream.of(beanNames).forEach(b -> System.out.println("bean name: " + b));
    }
}
