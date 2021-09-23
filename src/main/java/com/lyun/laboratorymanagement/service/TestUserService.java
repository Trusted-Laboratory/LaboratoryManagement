package com.lyun.laboratorymanagement.service;

import com.lyun.laboratorymanagement.entity.TestUser;

import java.util.List;

public interface TestUserService {
    void save(TestUser testUser);
    List<TestUser> findAll();
}
