package com.bp.spring.ioc.config;

import com.bp.spring.ioc.entities.Car;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: daiyu
 * @Date: 25/4/20 13:37
 * @Description:
 */
@ComponentScan("com.bp.spring.ioc")
@Configuration
public class MyConfigOfLifeCycle {

    @Bean(initMethod = "init", destroyMethod = "destory")
    public Car car() {
        return new Car();
    }
}
