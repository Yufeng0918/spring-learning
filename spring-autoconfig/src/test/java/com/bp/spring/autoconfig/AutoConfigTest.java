package com.bp.spring.autoconfig;

import com.bp.spring.autoconfig.controller.BookController;
import com.bp.spring.autoconfig.domain.Boss;
import com.bp.spring.autoconfig.domain.Car;
import com.bp.spring.autoconfig.domain.Red;
import com.bp.spring.autoconfig.service.BookService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;

public class AutoConfigTest {


    @Test
    public void testConfiguration() {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConfigurationConfig.class);
        BookController bookController = applicationContext.getBean(BookController.class);
        System.out.println(bookController);

        BookService bookService = applicationContext.getBean(BookService.class);
        System.out.println(bookService);
    }


    @Test
    public void testPrimary() {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConfigurationConfig.class);
        BookService bookService = applicationContext.getBean(BookService.class);
        System.out.println(bookService.getBookDao().label);
    }


    @Test
    public void testResourceInject() {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConfigurationConfig.class);
        BookService bookService = applicationContext.getBean(BookService.class);
        System.out.println(bookService.getTagDao());
        System.out.println(bookService.getSlotDao());
    }


    @Test
    public void testSetter() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConfigurationConfig.class);

        Boss boss = applicationContext.getBean(Boss.class);
        System.out.println(boss);
        Car car = applicationContext.getBean(Car.class);
        System.out.println(car == boss.getCar());
    }


    @Test
    public void testConstructor() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConfigurationConfig.class);

        BookController bookController = applicationContext.getBean(BookController.class);
        System.out.println(bookController.getBookService());
    }


    @Test
    public void testAware() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConfigurationConfig.class);

        Red red = applicationContext.getBean(Red.class);
        System.out.println(red.getApplicationContext());
    }


    @Test
    public void testProfile() {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();
        //1、创建一个applicationContext
        //2、设置需要激活的环境
        applicationContext.getEnvironment().setActiveProfiles("test");
        //3、注册主配置类
        applicationContext.register(ProfileConfig.class);
        //4、启动刷新容器
        applicationContext.refresh();


        String[] namesForType = applicationContext.getBeanNamesForType(DataSource.class);
        for (String string : namesForType) {
            System.out.println(string);
        }

//        Yellow bean = applicationContext.getBean(Yellow.class);
//        System.out.println(bean);
        applicationContext.close();
    }
}
