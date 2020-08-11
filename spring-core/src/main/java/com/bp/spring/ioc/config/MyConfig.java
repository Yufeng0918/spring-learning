package com.bp.spring.ioc.config;

import com.bp.spring.ioc.ColorFactoryBean;
import com.bp.spring.ioc.condition.LinuxCondition;
import com.bp.spring.ioc.condition.MyImportSelector;
import com.bp.spring.ioc.entities.Chinese;
import com.bp.spring.ioc.entities.Color;
import com.bp.spring.ioc.entities.Person;
import org.springframework.context.annotation.*;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * @Auther: daiyu
 * @Date: 24/4/20 22:37
 * @Description:
 */

@Configuration
@Conditional({LinuxCondition.class})
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
