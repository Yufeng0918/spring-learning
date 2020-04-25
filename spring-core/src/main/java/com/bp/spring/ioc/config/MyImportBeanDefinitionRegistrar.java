package com.bp.spring.ioc.config;

import com.bp.spring.ioc.entities.RainBow;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @Auther: daiyu
 * @Date: 25/4/20 09:24
 * @Description:
 */
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        boolean contains = registry.containsBeanDefinition("com.bp.spring.ioc.s01.bean.entities.Blue")
                           && registry.containsBeanDefinition("com.bp.spring.ioc.s01.bean.entities.Red");
        if (contains) {
            RootBeanDefinition rainBowBean = new RootBeanDefinition(RainBow.class);
            registry.registerBeanDefinition("rainBow", rainBowBean);
        }
    }
}
