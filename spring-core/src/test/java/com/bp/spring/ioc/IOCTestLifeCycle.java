package com.bp.spring.ioc;

import com.bp.spring.ioc.config.ApplicationConfig;
import com.bp.spring.ioc.config.MyConfig;
import com.bp.spring.ioc.config.MyConfigOfLifeCycle;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Auther: daiyu
 * @Date: 25/4/20 09:08
 * @Description:
 */
public class IOCTestLifeCycle {

    AnnotationConfigApplicationContext applicationContext =
            new AnnotationConfigApplicationContext(ApplicationConfig.class, MyConfig.class, MyConfigOfLifeCycle.class);

    @Test
    public void testLifeCycle() {


        Object object = applicationContext.getBean("car");
        applicationContext.close();
    }
}
