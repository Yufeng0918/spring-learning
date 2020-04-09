package com.bp.springboot;

import com.bp.springboot.bean.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * SpringBoot单元测试;
 *
 * 可以在测试期间很方便的类似编码一样进行自动注入等容器的功能
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBoot02ConfigApplicationTests {

	@Autowired
	Person person;

	@Autowired
	ApplicationContext ioc;

	@Test
	public void testHelloService(){
//		boolean b = ioc.containsBean("helloService");
		System.out.println("bean in beans.xml: " + ioc.containsBean("helloService"));
		System.out.println("bean in @Configuration's @Bean: " + ioc.containsBean("helloService02"));
	}


	@Test
	public void contextLoads() {
		System.out.println(person);
	}

}
