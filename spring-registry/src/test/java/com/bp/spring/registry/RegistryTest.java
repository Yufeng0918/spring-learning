package com.bp.spring.registry;

import com.bp.spring.registry.domain.Color;
import com.bp.spring.registry.domain.Person;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class RegistryTest {

    @Test
    public void testConfiguration() {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConfigurationConfig.class);
        Person bean = applicationContext.getBean(Person.class);
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


    @Test
    public void testComponentScanWithExcludeFilter() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ComponentScanExcludeFilterConfig.class);
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : definitionNames) {
            System.out.println(name);
        }
    }


    @Test
    public void testComponentScanWithIncludeFilter() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ComponentScanIncludeFilterConfig.class);
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : definitionNames) {
            System.out.println(name);
        }
    }


    @Test
    public void testComponentScanWithCustomFilter() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ComponentScanCustomFilterConfig.class);
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : definitionNames) {
            System.out.println(name);
        }
    }


    @Test
    public void testProptypeBean() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(PrototypeBeanConfig.class);
        System.out.println("ioc容器创建完成....");
        Object bean = applicationContext.getBean("person");
        Object bean2 = applicationContext.getBean("person");
        System.out.println(bean == bean2);
    }


    @Test
    public void testLazyBean() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(PrototypeBeanConfig.class);
        Object bean = applicationContext.getBean("person");
    }


    @Test
    public void testConditional() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConditionalConfig.class);
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : definitionNames) {
            System.out.println(name);
        }
    }


    @Test
    public void testImport() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ImportConfig.class);
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : definitionNames) {
            System.out.println(name);
        }
    }

    @Test
    public void testImportSelector() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ImportSelectorConfig.class);
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : definitionNames) {
            System.out.println(name);
        }
    }


    @Test
    public void testImportBeanDefinitionRegistrar() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ImportBeanDefinitionRegistrarConfig.class);
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : definitionNames) {
            System.out.println(name);
        }
    }


    @Test
    public void testFactoryBean() throws Exception {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConfigurationConfig.class);
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : definitionNames) {
            System.out.println(name);
        }

        Color color1  = (Color) applicationContext.getBean("colorFactoryBean");
        Color color2  = (Color) applicationContext.getBean("colorFactoryBean");

        ColorFactoryBean colorFactoryBean = (ColorFactoryBean) applicationContext.getBean("&colorFactoryBean");
        Color color3 = colorFactoryBean.getObject();
        System.out.println(colorFactoryBean.getClass());
        System.out.println(color1 == color2);
        System.out.println(color2 == color3);
    }
}
