package com.bp.spring.ioc.s01.bean.config;

import com.bp.spring.ioc.s01.bean.ColorFactoryBean;
import com.bp.spring.ioc.s01.bean.condition.LinuxCondition;
import com.bp.spring.ioc.s01.bean.condition.MyImportSelector;
import com.bp.spring.ioc.s01.bean.entities.Chinese;
import com.bp.spring.ioc.s01.bean.entities.Color;
import com.bp.spring.ioc.s01.bean.entities.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

import static org.springframework.beans.factory.config.BeanDefinition.*;

/**
 * @Auther: daiyu
 * @Date: 24/4/20 22:37
 * @Description:
 */

@Configuration
@Conditional({ LinuxCondition.class })
@Import({Color.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
public class MyConfig {

    @Scope(SCOPE_PROTOTYPE)
    @Bean
    public Person person() {
        return new Chinese();
    }

    @Bean
    public ColorFactoryBean colorFactoryBean() {
        return new ColorFactoryBean();
    }
}
