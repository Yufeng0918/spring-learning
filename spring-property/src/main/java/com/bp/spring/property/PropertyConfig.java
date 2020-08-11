package com.bp.spring.property;

import com.bp.spring.property.domain.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = {"classpath:/person.properties"})
@Configuration
public class PropertyConfig {

    @Bean
    public Person person() {
        return new Person();
    }
}