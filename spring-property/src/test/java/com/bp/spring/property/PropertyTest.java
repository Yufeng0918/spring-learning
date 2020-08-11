package com.bp.spring.property;

import com.bp.spring.property.domain.Person;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class PropertyTest {

    @Test
    public void testProperty(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(PropertyConfig.class);
        Person person = applicationContext.getBean(Person.class);
        System.out.println(person);
    }
}
