package com.bp.spring;

import com.bp.spring.aop.annotation.ApplicationConfig;
import com.bp.spring.aop.annotation.TxtService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Auther: daiyu
 * @Date: 26/4/20 09:17
 * @Description:
 */
public class IOCTestAOP {


    @Test
    public void testAOP() {
        ApplicationContext factory = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        TxtService txtService = (TxtService) factory.getBean("txtService");
        txtService.save("trx-01");
    }
}
