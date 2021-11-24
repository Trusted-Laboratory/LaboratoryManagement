package com.lyun.laboratorymanagement.controller;

import com.alibaba.fastjson.JSONObject;
import com.lyun.laboratorymanagement.entity.Image;
import com.lyun.laboratorymanagement.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/image")
public class ImageApi {

    @Autowired
    ImageService imageService;

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public JSONObject findAll(){
        JSONObject res = new JSONObject();
        res.put("images",imageService.findAll());
        return res;
    }

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public JSONObject getById(@RequestParam Integer id){
        if (id == null){
            return null;
        }
        JSONObject res = new JSONObject();
        res.put("image",imageService.getById(id));
        return res;
    }

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public JSONObject upload(@RequestBody JSONObject data, HttpServletResponse response, HttpServletRequest request){
        String img = data.getString("img");
        Image image;
        if (img != null){
            if(imageService.getByImg(img) == null){
                imageService.addImg(img);
            }
            image = imageService.getByImg(img);
            JSONObject suc = new JSONObject();
            suc.put("suc",true);
            suc.put("id",image.getId());
            return suc;
        }else {
            JSONObject fail = new JSONObject();
            fail.put("suc",false);
            return fail;
        }
    }
}
