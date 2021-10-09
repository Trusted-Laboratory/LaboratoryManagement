package com.lyun.laboratorymanagement.service;

import com.lyun.laboratorymanagement.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    boolean checkUser(String username,String password);
    int getPower(String username);
}
