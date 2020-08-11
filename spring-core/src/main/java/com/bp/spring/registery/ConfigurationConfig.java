package com.bp.spring.registery;

import com.bp.spring.ioc.domain.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//配置类==配置文件
@Configuration  //告诉Spring这是一个配置类
public class ConfigurationConfig {

    //给容器中注册一个Bean;类型为返回值的类型，id默认是用方法名作为id
    @Bean("person")
    public Person person01() {
        return new Person("lisi", 20);
    }

}
