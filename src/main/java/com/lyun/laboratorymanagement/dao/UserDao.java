package com.lyun.laboratorymanagement.dao;

import com.lyun.laboratorymanagement.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseDao<User>{
    User getUserById(int id);
    User getUserByName(String username);
}
