package com.bp.spring.ext;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IocTestExt {

    @Test
    public void test01() {

        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(ExtConfig.class);


        applicationContext.close();
    }
}
