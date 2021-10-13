package com.lyun.laboratorymanagement.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lyun.laboratorymanagement.entity.News;
import com.lyun.laboratorymanagement.entity.User;
import com.lyun.laboratorymanagement.service.NewsService;
import com.lyun.laboratorymanagement.service.UserService;
import com.lyun.laboratorymanagement.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@RequestMapping("/news")
@RestController
public class NewsApi {

    @Autowired
    private NewsService newsService;

    @Autowired
    private UserService userService;

    @RequestMapping("/num")
    public JSONObject newsNum(){
        JSONObject res = new JSONObject();
        List<News> newsList = newsService.findAll();
        res.put("num",newsList.size());
        return res;
    }

    @RequestMapping(value = "/find",method = RequestMethod.GET)
    public JSONObject pageNews(@RequestParam int page,@RequestParam int page_len){
        JSONObject res = new JSONObject();
        List<News> newsList = newsService.findAll();
        if (newsList.size() > page_len){
            if (page * 10 > (newsList.size()/10 + 1)*10){
                JSONObject fail = new JSONObject();
                fail.put("success",false);
                fail.put("result","page is out of bounds");
                return fail;
            }
            JSONArray news = new JSONArray();
            for (int i = (page-1)*page_len; i < Math.min(newsList.size(), page*10);i++){
                 news.add(newsList.get(i));
            }
            res.put("success",true);
            res.put("news",news);
        }else {
            JSONArray news = new JSONArray();
            news.addAll(newsList);
            res.put("success",true);
            res.put("news",news);
        }
        return res;
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public JSONObject addNews(@RequestBody JSONObject data, HttpServletResponse response, HttpServletRequest request){
        String token = CookieUtils.getCookie(request,"token");
        if (token == null){
            JSONObject notLogin = new JSONObject();
            notLogin.put("suc",false);
            notLogin.put("result,","not login");
            return notLogin;
        }
        if (userService.getPower(User.Token.tokens.get(token)) < 4){
            JSONObject permissionDenied = new JSONObject();
            permissionDenied.put("suc",false);
            permissionDenied.put("result","Permission denied");
            return permissionDenied;
        }
        String title = data.getString("title");
        String content = data.getString("content");
        if (title != null && content != null){
            Date date = new Date(System.currentTimeMillis());
            Time time = new Time(System.currentTimeMillis());
            News news = new News();
            news.setContent(content);
            news.setTitle(title);
            news.setDate(date);
            news.setTitle(title);
            news.setTime(time);
            newsService.addNews(news);
            JSONObject suc = new JSONObject();
            suc.put("suc",true);
            return suc;
        }else {
            JSONObject missingParameter = new JSONObject();
            missingParameter.put("suc",false);
            missingParameter.put("result","Missing parameter");
            return missingParameter;
        }
    }

    @RequestMapping(value = "/change",method = RequestMethod.POST)
    public JSONObject changeNews(@RequestBody JSONObject data,HttpServletResponse response, HttpServletRequest request){
        String token = CookieUtils.getCookie(request,"token");
        if (token == null){
            JSONObject notLogin = new JSONObject();
            notLogin.put("suc",false);
            notLogin.put("result,","not login");
            return notLogin;
        }
        if (User.Token.tokens.get(token) == null || userService.getPower(User.Token.tokens.get(token)) < 4){
            JSONObject permissionDenied = new JSONObject();
            permissionDenied.put("suc",false);
            permissionDenied.put("result","Permission denied");
            return permissionDenied;
        }
        String title = data.getString("title");
        String content = data.getString("content");
        Integer id = data.getInteger("id");
        if (title != null && content != null && id != null){
            if (newsService.changeNews(id,title,content)){
                JSONObject suc = new JSONObject();
                suc.put("suc",true);
                return suc;
            }else {
                JSONObject nonExistent = new JSONObject();
                nonExistent.put("suc",false);
                nonExistent.put("result","non-existent");
                return nonExistent;
            }

        }else {
            JSONObject missingParameter = new JSONObject();
            missingParameter.put("suc",false);
            missingParameter.put("result","Missing parameter");
            return missingParameter;
        }
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public JSONObject deleteNews(@RequestBody JSONObject data,HttpServletResponse response, HttpServletRequest request){
        String token = CookieUtils.getCookie(request,"token");
        if (token == null){
            JSONObject notLogin = new JSONObject();
            notLogin.put("suc",false);
            notLogin.put("result,","not login");
            return notLogin;
        }
        if (User.Token.tokens.get(token) == null || userService.getPower(User.Token.tokens.get(token)) < 4){
            JSONObject permissionDenied = new JSONObject();
            permissionDenied.put("suc",false);
            permissionDenied.put("result","Permission denied");
            return permissionDenied;
        }
        Integer id = data.getInteger("id");
        if (id != null){
            newsService.deleteNews(id);
            JSONObject suc = new JSONObject();
            suc.put("suc",true);
            return suc;
        }else {
            JSONObject missingParameter = new JSONObject();
            missingParameter.put("suc",false);
            missingParameter.put("result","Missing parameter");
            return missingParameter;
        }

    }

}
