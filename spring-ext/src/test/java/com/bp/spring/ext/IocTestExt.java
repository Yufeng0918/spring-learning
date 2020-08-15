package com.bp.spring.ext;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IocTestExt {

    @Test
    public void testBeanFactoryPostProcessor() {

        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(ExtConfig.class);
        applicationContext.close();
    }

    @Test
    public void testBeanDefinitionRegistryPostProcessor() {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(ExtConfig.class);
        applicationContext.close();
    }


    @Test
    public void testApplicationListener() {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(ExtConfig.class);
        applicationContext.close();
    }
}
