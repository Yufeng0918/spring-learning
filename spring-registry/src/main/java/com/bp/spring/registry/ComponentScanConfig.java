package com.bp.spring.registry;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//配置类==配置文件
@Configuration  //告诉Spring这是一个配置类
@ComponentScan("com.bp.spring.registry.*")
public class ComponentScanConfig {

}
