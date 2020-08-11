package com.bp.spring.autoconfig;

import com.bp.spring.autoconfig.dao.BookDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

//配置类==配置文件
@Configuration
@ComponentScan
public class ConfigurationConfig {


    @Primary
    @Bean("bookDao2")
    public BookDao bookDao(){
        BookDao bookDao = new BookDao();
        bookDao.label = 2;
        return bookDao;
    }
}
