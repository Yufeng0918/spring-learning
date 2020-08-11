package com.bp.spring.ioc.config;

import com.bp.spring.ioc.dao.BookDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: daiyu
 * @Date: 25/4/20 20:35
 * @Description:
 */
@Configuration
@ComponentScan({
        "com.bp.spring.ioc.service", "com.bp.spring.ioc.controller", "com.bp.spring.ioc.com.bp.spring.registry.dao",
        "com.bp.spring.ioc.entities"
})
public class MyConfigAutowired {

    @Bean("bookDao2")
    public BookDao bookDao() {
        return new BookDao();
    }
}
