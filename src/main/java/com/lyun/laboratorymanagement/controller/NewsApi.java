package com.lyun.laboratorymanagement.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lyun.laboratorymanagement.entity.News;
import com.lyun.laboratorymanagement.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/news")
@RestController
public class NewsApi {

    @Autowired
    private NewsService newsService;

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

}
