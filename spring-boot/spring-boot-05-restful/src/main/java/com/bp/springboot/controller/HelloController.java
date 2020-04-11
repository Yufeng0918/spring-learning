package com.bp.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther: daiyu
 * @Date: 11/4/20 08:36
 * @Description:
 */
@Controller
public class HelloController {


    @ResponseBody
    @RequestMapping("/hello")
    public String sayHello() {
        return "Hello";
    }
}
