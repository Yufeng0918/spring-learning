package com.bp.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(value = "com.bp.springboot.mapper")
@SpringBootApplication
public class SpringBoot08DataMybatisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBoot08DataMybatisApplication.class, args);
	}
}
