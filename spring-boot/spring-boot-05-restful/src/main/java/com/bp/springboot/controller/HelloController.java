package com.bp.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Map;

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

    @RequestMapping("success")
    public String success(Map<String, Object> map) {
        map.put("hello", "<h1>Hello</h1>");
        map.put("users", Arrays.asList("SG", "NY", "LD"));
        return "success";
    }
}
