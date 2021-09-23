package com.lyun.laboratorymanagement.service;

import com.lyun.laboratorymanagement.dao.TestUserDao;
import com.lyun.laboratorymanagement.entity.TestUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class TestUserServiceImpl implements TestUserService{

    @Autowired
    TestUserDao testUserDao;

    @Override
    public void save(TestUser testUser) {

    }

    @Override
    public List<TestUser> findAll() {
        return testUserDao.findAll();
    }
}
