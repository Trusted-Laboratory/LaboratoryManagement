package com.lyun.laboratorymanagement.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lyun.laboratorymanagement.entity.Member;
import com.lyun.laboratorymanagement.entity.User;
import com.lyun.laboratorymanagement.service.MemberService;
import com.lyun.laboratorymanagement.service.UserService;
import com.lyun.laboratorymanagement.utils.CookieUtils;
import com.lyun.laboratorymanagement.utils.ImageTools;
import com.lyun.laboratorymanagement.utils.PathTools;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/member")
public class MemberApi {

    @Autowired
    private MemberService memberService;
    @Autowired
    private UserService userService;


    @RequestMapping(value = "/num",method = RequestMethod.GET)
    public JSONObject getMemberNum(){
        JSONObject res = new JSONObject();
        int num = memberService.findAll().size();
        res.put("num",num);
        return res;
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public JSONObject getAllMemberInf(){
        JSONObject res = new JSONObject();
        JSONArray memberArray = new JSONArray();
        List<Member> members = memberService.findAll();
        memberArray.addAll(members);
        res.put("members",memberArray);
        return res;
    }

    @RequestMapping(value = "/new",method = RequestMethod.POST)
    public JSONObject newMember(@RequestBody JSONObject data, HttpServletResponse response, HttpServletRequest request){
        String token = CookieUtils.getCookie(request,"token");
        if (token != null && User.Token.tokens.containsKey(token)){
            if (userService.getPower(User.Token.tokens.get(token)) < 5){
                JSONObject lowPower = new JSONObject();
                lowPower.put("suc",false);
                lowPower.put("result","Permission denied");
                return lowPower;
            }
            String name = data.getString("name");
            String sex = data.getString("sex");
            String brief_introduction = data.getString("brief_introduction");
            if (name == null || sex == null || brief_introduction == null){
                JSONObject fail = new JSONObject();
                fail.put("suc",false);
                return fail;
            }else {
                Member member = new Member();
                member.setName(name);
                member.setSex(sex);
                member.setBrief_introduction(brief_introduction);
                memberService.addMember(member);
                JSONObject suc = new JSONObject();
                suc.put("suc",true);
                return suc;
            }
        }else {
            JSONObject notLogin = new JSONObject();
            notLogin.put("suc",false);
            notLogin.put("result","not login");
            return notLogin;
        }

    }

    @RequestMapping(value = "/photo",method = RequestMethod.GET)
    public JSONObject getMemberPhoto(@RequestParam String name){
        File photo = new File(PathTools.getRunPath() + "/member_photo/" + name + ".jpg");
        if (photo.exists()){
            BufferedImage input = null;
            try {
                input = ImageIO.read(photo);
            } catch (IOException e) {
                e.printStackTrace();
            }
            JSONObject suc = new JSONObject();
            suc.put("success",true);
            suc.put("img",ImageTools.imgToBase64(input));
            return suc;
        }else {
            JSONObject fail = new JSONObject();
            fail.put("suc",false);
            return fail;
        }
    }

    @SneakyThrows
    @RequestMapping(value = "/change/photo",method = RequestMethod.POST)
    public JSONObject changeMemberPhoto(@RequestBody JSONObject data, HttpServletResponse response, HttpServletRequest request){
        String token = CookieUtils.getCookie(request,"token");
        if (token != null && User.Token.tokens.containsKey(token)){
            if (userService.getPower(User.Token.tokens.get(token)) < 5){
                JSONObject lowPower = new JSONObject();
                lowPower.put("suc",false);
                lowPower.put("result","Permission denied");
                return lowPower;
            }
            String image = data.getString("img");
            String name = data.getString("name");
            if (image != null && name != null){
                Member member = memberService.findByName(name);
                if (member != null){
                    File photo = new File(PathTools.getRunPath() + "/member_photo/" + name + ".jpg");
                    BufferedImage img = ImageTools.base64ToImg(image);
                    ImageIO.write(img,"jpg",photo);
                    JSONObject suc = new JSONObject();
                    suc.put("suc",true);
                    return suc;
                }else {
                    JSONObject notExist = new JSONObject();
                    notExist.put("suc",false);
                    notExist.put("result","Member not exist");
                    return notExist;
                }
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
