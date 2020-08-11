package com.bp.spring.registry;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

//配置类==配置文件
@Configuration  //告诉Spring这是一个配置类
@ComponentScan(value = "com.bp.spring.registry.*",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.CUSTOM, classes = {MyTypeFilter.class})
        }, useDefaultFilters = false)
public class ComponentScanCustomFilterConfig {

}
