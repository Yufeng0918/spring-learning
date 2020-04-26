package com.bp.spring;

import com.bp.spring.aop.annotation.ApplicationConfig;
import com.bp.spring.aop.annotation.TxtService;
import com.bp.spring.aop.tx.TxConfig;
import com.bp.spring.aop.tx.service.UserService;
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

    @Test
    public void testTx() {

        ApplicationContext factory = new AnnotationConfigApplicationContext(TxConfig.class);
        UserService userService = factory.getBean(UserService.class);
        userService.insertUser();
    }
}
