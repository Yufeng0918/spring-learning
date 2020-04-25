package com.bp.spring.ioc;

import com.bp.spring.ioc.config.MyConfigAutowired;
import com.bp.spring.ioc.config.MyConfigOfPropertyValue;
import com.bp.spring.ioc.dao.BookDao;
import com.bp.spring.ioc.entities.Chinese;
import com.bp.spring.ioc.service.BookService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.stream.Stream;

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
    }

}
