package com.yang.controller;

import com.yang.mapper.UserMapper;
import com.yang.pojo.User;
import com.yang.util.PasswordUtil;
import com.yang.util.SensitiveWordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Register {
    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/ferry/register")
    public String register(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           Model model) {
        List<String> list = new ArrayList<>();
        list.add("尼玛");
        list.add("站长");
        list.add("国家领导人");
        list.add("操");
        SensitiveWordUtil.initMap(list);
        if (username.length() <= 15) {
            if (PasswordUtil.checkPassword(password)) {
                if (!SensitiveWordUtil.mathWords(username)) {
                    if (userMapper.queryUserByUsername(username) == null) {
                        userMapper.addUser(new User(null , username, password, null, null));
                        return "ok";
                    } else {
                        return "already exist";
                    }
                }
                return "用户名含有违规词汇";
            }
            return "密码强度不符";
        }
        return "用户名过长";
    }
}
