package com.bp.spring.ioc;

import com.bp.spring.ioc.domain.Person;
import com.bp.spring.registery.ComponentScanConfig;
import com.bp.spring.registery.ConfigurationConfig;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationTest {


    @Test
    public void testConfiguration() {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConfigurationConfig.class);
        com.bp.spring.ioc.domain.Person bean = applicationContext.getBean(com.bp.spring.ioc.domain.Person.class);
        System.out.println(bean);

        String[] namesForType = applicationContext.getBeanNamesForType(Person.class);
        for (String name : namesForType) {
            System.out.println(name);
        }
    }


    @Test
    public void testComponentScan() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ComponentScanConfig.class);
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : definitionNames) {
            System.out.println(name);
        }
    }
}
