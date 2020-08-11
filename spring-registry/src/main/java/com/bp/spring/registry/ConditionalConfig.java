package com.bp.spring.registry;

import com.bp.spring.registry.condition.LinuxCondition;
import com.bp.spring.registry.condition.WindowsCondition;
import com.bp.spring.registry.domain.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConditionalConfig {


    @Bean("bill")
    @Conditional(WindowsCondition.class)
    public Person person01() {
        return new Person("Bill Gates", 62);
    }

    @Conditional(LinuxCondition.class)
    @Bean("linus")
    public Person person02() {
        return new Person("linus", 48);
    }
}
