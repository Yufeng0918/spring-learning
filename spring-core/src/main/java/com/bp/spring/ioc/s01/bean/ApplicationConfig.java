package com.bp.spring.ioc.s01.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by day68c on 2/3/2017.
 */

@Configuration
@ComponentScan()
public class ApplicationConfig {

    @Bean
    public Chinese chinese() {
        return new Chinese();
    }

    @Bean
    public American american() {
        return new American();
    }
}
