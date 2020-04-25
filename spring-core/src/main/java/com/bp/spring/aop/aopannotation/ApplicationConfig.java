package com.bp.spring.aop.aopannotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by day68c on 9/3/2017.
 */

@Configuration
@ComponentScan()
@EnableAspectJAutoProxy
public class ApplicationConfig {


    @Bean
    public TxtAdvice txtAdvice() {
        return new TxtAdvice();
    }

}
