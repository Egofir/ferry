package com.yang.ferry.service;

import com.yang.ferry.exception.FerryException;
import com.yang.ferry.model.pojo.User;

public interface UserService {
    User getUser();

    void register(String username, String password) throws FerryException;

    void extracted(String username, String password) throws FerryException;

    void setSensitiveWord();
}
