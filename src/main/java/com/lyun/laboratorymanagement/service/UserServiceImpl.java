package com.lyun.laboratorymanagement.service;

import com.lyun.laboratorymanagement.dao.UserDao;
import com.lyun.laboratorymanagement.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    UserDao userDao;

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public boolean checkUser(String username, String password) {
        User user = userDao.getUserByName(username);
        if (user != null){
            return DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)).equals(user.getPassword());
        }else {
            return false;
        }
    }

    @Override
    public int getPower(String username) {
        User user = userDao.getUserByName(username);
        return user.getPower();
    }
}
