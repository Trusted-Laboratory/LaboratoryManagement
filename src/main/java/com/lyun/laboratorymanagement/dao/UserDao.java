package com.lyun.laboratorymanagement.dao;

import com.lyun.laboratorymanagement.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseDao<User>{
    User getUserById(int id);
    User getUserByName(String username);
    void delUser(int id);
    void changeUser(String username,String password,int power,String realName);
}
