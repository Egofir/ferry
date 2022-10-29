package com.yang.ferry.controller;

import com.yang.ferry.common.ApiRestResponse;
import com.yang.ferry.exception.FerryException;
import com.yang.ferry.exception.FerryExceptionEnum;
import com.yang.ferry.model.dao.SensitiveWordMapper;
import com.yang.ferry.model.pojo.SensitiveWord;
import com.yang.ferry.model.pojo.User;
import com.yang.ferry.service.UserService;
import com.yang.ferry.util.PasswordUtil;
import com.yang.ferry.util.SensitiveWordUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    @Resource
    UserService userService;

    @GetMapping("/test")
    public User personalPage() {
        return userService.getUser();
    }

    @PostMapping("/ferry/register")
    public ApiRestResponse register(@RequestParam("username") String username,
                                    @RequestParam("password") String password) throws FerryException {
        if (StringUtils.isEmpty(username)) {
            return ApiRestResponse.error(FerryExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isEmpty(password)) {
            return ApiRestResponse.error(FerryExceptionEnum.NEED_PASSWORD);
        }
        userService.setSensitiveWord();
        if (SensitiveWordUtil.mathWords(username)) {
            return ApiRestResponse.error(FerryExceptionEnum.SENSITIVEWORD_EXISTED);
        }
        if (!PasswordUtil.checkPassword(password)) {
            return ApiRestResponse.error(FerryExceptionEnum.PASSWORD_NOT_CONFORM);
        }
        userService.register(username, password);
        return ApiRestResponse.success();
    }
}
