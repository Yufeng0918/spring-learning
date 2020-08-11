package com.bp.spring.registry;

import com.bp.spring.registry.domain.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

//配置类==配置文件
@Configuration  //告诉Spring这是一个配置类
public class PrototypeBeanConfig {

    //给容器中注册一个Bean;类型为返回值的类型，id默认是用方法名作为id
    @Scope("prototype")
    @Bean("person")
    public Person person01() {
        return new Person("lisi", 20);
    }

}
