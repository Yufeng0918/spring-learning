package com.bp.spring.lifecycle;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.bp.spring.lifecycle")
public class LifecyecleConfig {

    @Bean(initMethod="init",destroyMethod="detory")
    public Car car(){
        return new Car();
    }

    @Bean
    public Cat cat() {
        return new Cat();
    }


    @Bean
    public Dog dog() {
        return new Dog();
    }
}
