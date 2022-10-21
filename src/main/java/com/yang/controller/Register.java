package com.yang.controller;

import com.yang.mapper.SensitiveWordMapper;
import com.yang.mapper.UserMapper;
import com.yang.pojo.SensitiveWord;
import com.yang.pojo.User;
import com.yang.util.PasswordUtil;
import com.yang.util.SensitiveWordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Register {
    @Resource
    private UserMapper userMapper;
    @Resource
    private SensitiveWordMapper sensitiveWordMapper;

    @RequestMapping("/ferry/register")
    public String register(@RequestParam("username") String username,
                           @RequestParam("password") String password) {
        List<String> list = new ArrayList<>();
        List<SensitiveWord> sensitiveWordList = sensitiveWordMapper.querySensitiveWordList();
        for (int i = 0; i < sensitiveWordList.size(); i++) {
            SensitiveWord sensitiveWord = sensitiveWordList.get(i);
            list.add(sensitiveWord.getValue());
        }
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
