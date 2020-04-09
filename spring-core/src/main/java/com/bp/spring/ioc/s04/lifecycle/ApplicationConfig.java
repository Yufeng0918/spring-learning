package com.bp.spring.ioc.s04.lifecycle;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by day68c on 2/3/2017.
 */

@Configuration
@ComponentScan
public class ApplicationConfig {

    @Bean(initMethod = "customizeInit", destroyMethod = "customizeDestroy")
    public SpringBean springBean() {
        return new SpringBean();
    }

}
