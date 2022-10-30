package com.yang.ferry.service.impl;

import com.yang.ferry.exception.FerryException;
import com.yang.ferry.exception.FerryExceptionEnum;
import com.yang.ferry.model.dao.SensitiveWordMapper;
import com.yang.ferry.model.dao.UserMapper;
import com.yang.ferry.model.pojo.SensitiveWord;
import com.yang.ferry.model.pojo.User;
import com.yang.ferry.service.UserService;
import com.yang.ferry.util.MD5Utils;
import com.yang.ferry.util.SensitiveWordUtil;
import com.yang.ferry.util.SimpleRedisLock;
import org.springframework.aop.framework.AopContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private SensitiveWordMapper sensitiveWordMapper;

    @Override
    public User getUser() {
        return userMapper.selectByPrimaryKey(2);
    }

    @Override
    public void register(String username, String password) throws FerryException {
        SimpleRedisLock lock = new SimpleRedisLock("register:" + username, stringRedisTemplate);
        boolean isLock = lock.tryLock(5);
        if (!isLock) {
            throw new FerryException(FerryExceptionEnum.NAME_EXISTED);
        }
        try {
            UserService proxy = (UserService) AopContext.currentProxy();
            proxy.extracted(username, password);
        } finally {
            lock.unlock();
        }
    }

    @Transactional
    public synchronized void extracted(String username, String password) throws FerryException {
        User result = userMapper.queryUserByUsername(username);
        if (result != null) {
            throw new FerryException(FerryExceptionEnum.NAME_EXISTED);
        }
        User user = new User();
        user.setUsername(username);
        try {
            user.setPassword(MD5Utils.getMD5Str(password));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        int count = userMapper.addUser(user);
        if (count == 0) {
            throw new FerryException(FerryExceptionEnum.INSERT_FAILED);
        }
    }

    @Override
    public void setSensitiveWord() {
        List<String> list = new ArrayList<>();
        List<SensitiveWord> sensitiveWordList = sensitiveWordMapper.querySensitiveWordList();
        for (int i = 0; i < sensitiveWordList.size(); i++) {
            SensitiveWord sensitiveWord = sensitiveWordList.get(i);
            list.add(sensitiveWord.getValue());
        }
        SensitiveWordUtil.initMap(list);
    }
}
