package com.lyun.laboratorymanagement.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lyun.laboratorymanagement.service.TestUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class TestApi {

    @Autowired
    private TestUserService testUserService;

    @RequestMapping("/test_api")
    public JSONObject testApi(HttpServletResponse response, HttpServletRequest request){
        JSONObject res = new JSONObject();
        JSONArray users = new JSONArray();
        users.addAll(testUserService.findAll());
        res.put("users",users);
        return res;
    }
}
