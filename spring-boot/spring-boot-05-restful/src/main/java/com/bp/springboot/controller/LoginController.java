package com.bp.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Auther: daiyu
 * @Date: 12/4/20 23:26
 * @Description:
 */
@Controller
public class LoginController {

    @PostMapping("/user/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Map<String, Object> map) {

        if ("123456".equals(password)) {
            return "dashboard";
        }

        map.put("msg", "username and password is incorrect");
        return "login";
    }
}
