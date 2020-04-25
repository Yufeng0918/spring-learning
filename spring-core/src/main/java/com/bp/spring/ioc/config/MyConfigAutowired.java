package com.bp.spring.ioc.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: daiyu
 * @Date: 25/4/20 20:35
 * @Description:
 */
@Configuration
@ComponentScan({"com.bp.spring.ioc.service", "com.bp.spring.ioc.controller", "com.bp.spring.ioc.dao"})
public class MyConfigAutowired {
}
