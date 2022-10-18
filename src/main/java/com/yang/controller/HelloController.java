package com.yang.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/ferry")
public class HelloController {
    @GetMapping()
    public String welcome(HttpServletResponse response, HttpServletRequest request) {
        Cookie cookie = new Cookie("remember", "1");
        cookie.setMaxAge(24 * 60 * 60);
        response.addCookie(cookie);
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie1 : cookies) {
                if (cookie1.getName().equals("remember")) {
                    return "嗨，欢迎您再次到 Ferry.";
                }
            }
        }
        return "嗨，欢迎您来到 Ferry.";
    }
}
