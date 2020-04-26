package com.bp.spring.ioc;

import com.bp.spring.ioc.config.MyConfigAutowired;
import com.bp.spring.ioc.dao.BookDao;
import com.bp.spring.ioc.entities.Boss;
import com.bp.spring.ioc.entities.Car;
import com.bp.spring.ioc.entities.Red;
import com.bp.spring.ioc.service.BookService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Auther: daiyu
 * @Date: 25/4/20 09:08
 * @Description:
 */
public class IOCAutowiredTest {

    AnnotationConfigApplicationContext applicationContext =
            new AnnotationConfigApplicationContext(MyConfigAutowired.class);

    @Test
    public void testAutowired() {

        BookService bookService = applicationContext.getBean(BookService.class);
        BookDao bookDao = applicationContext.getBean(BookDao.class);
        System.out.println(bookService.toString());
        System.out.println(bookDao == bookService.getBookDao());

        Boss boss = applicationContext.getBean(Boss.class);
        Car car = applicationContext.getBean(Car.class);
        System.out.println(boss.getCar() == car);

        Red red = applicationContext.getBean(Red.class);
        System.out.println(red.getApplicationContext());
    }

}
