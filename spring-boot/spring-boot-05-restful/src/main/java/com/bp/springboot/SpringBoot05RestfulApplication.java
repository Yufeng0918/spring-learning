package com.bp.springboot;

/**
 * @Auther: daiyu
 * @Date: 11/4/20 08:26
 * @Description:
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;

@SpringBootApplication
public class SpringBoot05RestfulApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot05RestfulApplication.class, args);
    }

    @Bean
    public ViewResolver myViewResolver() {
        return new MyViewResolver();
    }

    private static class MyViewResolver implements ViewResolver {
        @Override
        public View resolveViewName(String viewName, Locale locale) throws Exception {
            return null;
        }
    }
}


