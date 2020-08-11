package com.bp.spring.registry;

import com.bp.spring.registry.controller.BookController;
import com.bp.spring.registry.domain.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

//配置类==配置文件
@Configuration  //告诉Spring这是一个配置类
@ComponentScan(value = "com.bp.spring.registry.*",
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = BookController.class)})
public class ComponentScanExcludeFilterConfig {

    @Bean("person")
    public Person person01() {
        return new Person("lisi", 20);
    }
}
