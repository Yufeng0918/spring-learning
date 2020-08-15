package com.bp.spring.ext;

import com.bp.spring.ext.entities.Blue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan("com.bp.spring.ext")
@Configuration
public class ExtConfig {

    @Bean
    public Blue blue() {
        return new Blue();
    }
}

