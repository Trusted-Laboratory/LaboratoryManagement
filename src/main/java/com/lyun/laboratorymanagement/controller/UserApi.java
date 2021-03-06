package com.lyun.laboratorymanagement.controller;

import com.alibaba.fastjson.JSONObject;
import com.lyun.laboratorymanagement.entity.User;
import com.lyun.laboratorymanagement.service.UserService;
import com.lyun.laboratorymanagement.utils.CookieUtils;
import javafx.scene.control.TableRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
public class UserApi {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public JSONObject JSONObject(@RequestBody JSONObject data, HttpServletResponse response){
        String username = data.getString("username");
        String password = data.getString("password");
        if (username != null && password != null && userService.checkUser(username,password)){
            JSONObject suc = new JSONObject();
            suc.put("suc",true);
            String token = User.Token.createToken(username);
            CookieUtils.writeCookie(response,"token",token,3600);
            return suc;
        }else {
            JSONObject fail = new JSONObject();
            fail.put("suc",false);
            return fail;
        }
    }

    @RequestMapping(value = "/check",method = RequestMethod.GET)
    public JSONObject check(HttpServletRequest request){
        String token = CookieUtils.getCookie(request,"token");
        if (token != null && User.Token.tokens.containsKey(token)){
            JSONObject login = new JSONObject();
            login.put("login",true);
            login.put("username",User.Token.tokens.get(token));
            return login;
        }else {
            JSONObject notLogin = new JSONObject();
            notLogin.put("login",false);
            return notLogin;
        }
    }

    @RequestMapping(value = "/power",method = RequestMethod.GET)
    public JSONObject getUserPower(HttpServletRequest request){

        String token = CookieUtils.getCookie(request,"token");
        if (token != null && User.Token.tokens.containsKey(token)){
            JSONObject power = new JSONObject();
            power.put("suc",true);
            power.put("power",userService.getPower(User.Token.tokens.get(token)));
            return power;
        }else {
            JSONObject notLogin = new JSONObject();
            notLogin.put("suc",false);
            notLogin.put("result","not login");
            return notLogin;
        }
    }

    @RequestMapping(value = "/del",method = RequestMethod.POST)
    public JSONObject delUser(@RequestBody JSONObject data, HttpServletResponse response,HttpServletRequest request){
        String token = CookieUtils.getCookie(request,"token");
        Integer id = data.getInteger("id");
        if (token != null && User.Token.tokens.containsKey(token)){
            if (id == null){
                JSONObject missingParameter = new JSONObject();
                missingParameter.put("suc",false);
                missingParameter.put("result","Missing parameter");
                return missingParameter;
            }
            int power = userService.getPower(User.Token.tokens.get(token));
            if (power < 5){
                JSONObject lowPower = new JSONObject();
                lowPower.put("suc",false);
                lowPower.put("result","Permission denied");
                return lowPower;
            }
            userService.delUser(id);
            JSONObject suc = new JSONObject();
            suc.put("suc", true);
            return suc;
        }else {
            JSONObject notLogin = new JSONObject();
            notLogin.put("suc",false);
            notLogin.put("result","not login");
            return notLogin;
        }
    }


    @RequestMapping(value = "/change",method = RequestMethod.POST)
    public JSONObject changeUserInf(@RequestBody JSONObject data, HttpServletResponse response,HttpServletRequest request){
        String token = CookieUtils.getCookie(request,"token");
        if (token != null && User.Token.tokens.containsKey(token)){
            String realName = User.Token.tokens.get(token);
            String username = data.getString("username");
            String password = data.getString("password");
            Integer power = data.getInteger("power");
            if (username != null && password != null && power != null){
                if (power > userService.getPower(realName)){
                    JSONObject lowPower = new JSONObject();
                    lowPower.put("suc",false);
                    lowPower.put("result","Permission denied");
                    return lowPower;
                }
                userService.changeUser(username,password,power,realName);
                JSONObject suc = new JSONObject();
                suc.put("suc",true);
                return suc;
            }else {
                JSONObject missingParameter = new JSONObject();
                missingParameter.put("suc",false);
                missingParameter.put("result","Missing parameter");
                return missingParameter;
            }
        }else {
            JSONObject notLogin = new JSONObject();
            notLogin.put("suc",false);
            notLogin.put("result","not login");
            return notLogin;
        }

    }
}
