package com.yang.mapper;

import com.yang.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    List<User> queryUserList();
    User queryUserByUsername(String username);
    void addUser(User user);
}
