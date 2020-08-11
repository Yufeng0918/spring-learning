package com.bp.spring.registry.condition;

import com.bp.spring.registry.domain.RainBow;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;


public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {


	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		
		boolean definition1 = registry.containsBeanDefinition("com.bp.spring.registry.domain.Red");
		boolean definition2 = registry.containsBeanDefinition("com.bp.spring.registry.domain.Blue");
		if(definition1 && definition2){
			RootBeanDefinition beanDefinition = new RootBeanDefinition(RainBow.class);
			registry.registerBeanDefinition("rainBow", beanDefinition);
		}
	}

}
