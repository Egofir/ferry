package com.yang.ferry.model.dao;

import com.yang.ferry.model.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    List<User> queryUserList();
    User queryUserByUsername(String username);
    int addUser(User user);
    User selectByPrimaryKey(int id);
    User selectLogin(String username);
}
