package com.yang.ferry.controller;

import com.yang.ferry.model.dao.SensitiveWordMapper;
import com.yang.ferry.model.dao.UserMapper;
import com.yang.ferry.model.pojo.SensitiveWord;
import com.yang.ferry.model.pojo.User;
import com.yang.ferry.util.PasswordUtil;
import com.yang.ferry.util.SensitiveWordUtil;
import com.yang.ferry.util.SimpleRedisLock;
import org.springframework.data.redis.core.StringRedisTemplate;
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
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/ferry/register")
    public String register(@RequestParam("username") String username,
                           @RequestParam("password") String password) {
        SimpleRedisLock lock = new SimpleRedisLock("username:" + username, stringRedisTemplate);
        boolean isLock = lock.tryLock(5);
        if (!isLock) {
            return "用户名重复";
        }
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
                        User user = new User();
                        user.setUsername(username);
                        user.setPassword(password);
                        userMapper.addUser(user);
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
