package com.bp.spring.ioc.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by day68c on 2/3/2017.
 */

@Service
public class SpringBean implements InitializingBean, DisposableBean {

    public SpringBean() {
        System.out.println("SpringBean constructor");
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet");
    }


    public void customizeInit() {
        System.out.println("customizeInit");
    }

    public void customizeDestroy() {
        System.out.println("customizeDestroy");
    }


    @Override
    public void destroy() throws Exception {
        System.out.println("destory");
    }
}
