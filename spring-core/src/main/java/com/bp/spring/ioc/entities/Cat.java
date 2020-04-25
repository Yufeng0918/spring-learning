package com.bp.spring.ioc.entities;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @Auther: daiyu
 * @Date: 25/4/20 14:51
 * @Description:
 */
@Component
public class Cat implements InitializingBean, DisposableBean {

    public Cat() {
        System.out.println("cat constructor");
    }


    @Override
    public void destroy() throws Exception {
        System.out.println("cat destory");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("cat after properties set");
    }
}
