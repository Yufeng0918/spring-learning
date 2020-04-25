package com.bp.spring.ioc.s01.bean.config;

import com.bp.spring.ioc.s01.bean.American;
import com.bp.spring.ioc.s01.bean.Chinese;
import com.bp.spring.ioc.s01.bean.condition.LinuxCondition;
import com.bp.spring.ioc.s01.bean.condition.WindowsCondition;
import com.bp.spring.ioc.s01.bean.dao.BookDao;
import com.bp.spring.ioc.s01.bean.service.BookService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

/**
 * Created by day68c on 2/3/2017.
 */

@Configuration
@ComponentScan(value = "com.bp.spring.ioc.s01.bean", includeFilters = {
//        @Filter(type = FilterType.ANNOTATION, classes = { Controller.class}),
//        @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = { BookService.class}),
        @Filter(type = FilterType.CUSTOM, classes = {MyFilter.class})
}, useDefaultFilters = false)
public class ApplicationConfig {

    @Bean
    public Chinese chinese() {
        System.out.println("add chinese");
        return new Chinese();
    }

    @Bean("bill")
    @Conditional({ WindowsCondition.class })
    public Chinese chinese1()  {
        return new Chinese("Bill Gates", 62);
    }

    @Bean("linus")
    @Conditional({ LinuxCondition.class})
    public Chinese chinese2() {
        return new Chinese("Linus", 50);
    }

    @Lazy
    @Bean
    public American american() {
        System.out.println("add american");
        return new American();
    }
}
