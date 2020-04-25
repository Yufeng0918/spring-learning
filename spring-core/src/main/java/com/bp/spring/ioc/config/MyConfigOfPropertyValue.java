package com.bp.spring.ioc.config;

import com.bp.spring.ioc.entities.Chinese;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @Auther: daiyu
 * @Date: 25/4/20 19:44
 * @Description:
 */
@Configuration
@PropertySource("classpath:/person.properties")
public class MyConfigOfPropertyValue {

    @Bean
    public Chinese person() {
        return new Chinese();
    }
}
