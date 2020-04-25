package com.bp.spring.ioc.config;

import com.bp.spring.ioc.ColorFactoryBean;
import com.bp.spring.ioc.condition.LinuxCondition;
import com.bp.spring.ioc.condition.MyImportSelector;
import com.bp.spring.ioc.entities.Chinese;
import com.bp.spring.ioc.entities.Color;
import com.bp.spring.ioc.entities.Person;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

import javax.sql.DataSource;

import static org.springframework.beans.factory.config.BeanDefinition.*;

/**
 * @Auther: daiyu
 * @Date: 24/4/20 22:37
 * @Description:
 */

@Configuration
@Conditional({ LinuxCondition.class })
@Import({ Color.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class })
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
