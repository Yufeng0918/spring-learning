package com.bp.spring.ioc.s01.bean.config;

import com.bp.spring.ioc.s01.bean.Chinese;
import com.bp.spring.ioc.s01.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import static org.springframework.beans.factory.config.BeanDefinition.*;

/**
 * @Auther: daiyu
 * @Date: 24/4/20 22:37
 * @Description:
 */

@Configuration
public class MyConfig {

    @Scope(SCOPE_PROTOTYPE)
    @Bean
    public Person person() {
        return new Chinese();
    }
}
