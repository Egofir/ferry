package com.yang.mapper;

import com.yang.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Mapper
@Repository
public interface UserMapper {
    List<User> queryUserList();
    User queryUserByUsername(String username);
    int addUser(User user);
}
