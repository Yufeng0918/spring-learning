package com.bp.spring.ioc;

import com.bp.spring.ioc.config.MyConfigOfProfile;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;
import java.util.stream.Stream;

/**
 * @Auther: daiyu
 * @Date: 25/4/20 09:08
 * @Description:
 */
public class IOCProfileTest {

    AnnotationConfigApplicationContext applicationContext =
            new AnnotationConfigApplicationContext();

    @Test
    public void testProfile() {
        applicationContext.getEnvironment().setActiveProfiles("test");
        applicationContext.register(MyConfigOfProfile.class);
        applicationContext.refresh();
        String[] datasources = applicationContext.getBeanNamesForType(DataSource.class);
        Stream.of(datasources).forEach(d -> System.out.println(d));
    }

    private void printBeans() {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        Stream.of(beanNames).forEach(b -> System.out.println("bean name: " + b));
    }
}
