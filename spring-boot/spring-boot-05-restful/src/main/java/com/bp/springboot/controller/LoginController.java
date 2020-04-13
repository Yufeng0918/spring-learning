package com.bp.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Auther: daiyu
 * @Date: 12/4/20 23:26
 * @Description:
 */
@Controller
public class LoginController {

    @PostMapping("/user/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session, Map<String, Object> map) {

        if ("123".equals(password)) {
            session.setAttribute("loginUser", username);
            return "redirect:/main.html";
        }

        map.put("msg", "username and password is incorrect");
        return "login";
    }
}
