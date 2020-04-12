package com.bp.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auther: daiyu
 * @Date: 12/4/20 23:26
 * @Description:
 */
@Controller
public class LoginController {

    @PostMapping("/user/login")
    public String login() {
        return "dashboard";
    }
}
