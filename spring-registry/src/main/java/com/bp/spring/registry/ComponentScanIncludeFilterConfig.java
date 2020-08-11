package com.bp.spring.registry;

import com.bp.spring.registry.dao.BookDao;
import com.bp.spring.registry.service.BookService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

//配置类==配置文件
@Configuration  //告诉Spring这是一个配置类
@ComponentScan(value = "com.bp.spring.registry.*",
        includeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {BookService.class, BookDao.class})}, useDefaultFilters = false)
public class ComponentScanIncludeFilterConfig {

}
