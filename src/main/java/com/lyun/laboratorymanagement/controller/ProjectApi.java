package com.lyun.laboratorymanagement.controller;

import com.alibaba.fastjson.JSONObject;
import com.lyun.laboratorymanagement.entity.News;
import com.lyun.laboratorymanagement.entity.Project;
import com.lyun.laboratorymanagement.entity.User;
import com.lyun.laboratorymanagement.service.ProjectService;
import com.lyun.laboratorymanagement.service.UserService;
import com.lyun.laboratorymanagement.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.sql.Time;

@RestController
@RequestMapping("/project")
public class ProjectApi {

    @Autowired
    ProjectService projectService;
    @Autowired
    UserService userService;

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public JSONObject findAll(){
        JSONObject res = new JSONObject();
        res.put("projects",projectService.findAll());
        return res;
    }

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public JSONObject getProject(@RequestParam int id){
        JSONObject res = new JSONObject();
        res.put("project",projectService.getById(id));
        return res;
    }

    @RequestMapping(value = "/new",method = RequestMethod.POST)
    public JSONObject newProject(@RequestBody JSONObject data, HttpServletResponse response, HttpServletRequest request){
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
        String type = data.getString("type");
        String summarize = data.getString("summarize");
        if (title != null && content != null && type!=null && summarize != null){
            if (type.length() > 4){
                JSONObject typeTooLong = new JSONObject();
                typeTooLong.put("suc",false);
                typeTooLong.put("result","type is too long");
                return typeTooLong;
            }
            Date date = new Date(System.currentTimeMillis());
            Time time = new Time(System.currentTimeMillis());
            Project project = new Project();
            project.setContent(content);
            project.setTitle(title);
            project.setSummarize(summarize);
            project.setType(type);
            project.setDate(date);
            project.setTime(time);
            projectService.newProject(project);
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
    public JSONObject changeProject(@RequestBody JSONObject data, HttpServletResponse response, HttpServletRequest request){
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
        String type = data.getString("type");
        String summarize = data.getString("summarize");
        Integer id = data.getInteger("id");
        if (title != null && content != null && type!=null && summarize != null && id !=null){
            if (type.length() > 4){
                JSONObject typeTooLong = new JSONObject();
                typeTooLong.put("suc",false);
                typeTooLong.put("result","type is too long");
                return typeTooLong;
            }
            if (projectService.getById(id) == null){
                    JSONObject notExist = new JSONObject();
                    notExist.put("suc",false);
                    notExist.put("result","id not exist");
                    return notExist;
            }
            projectService.changeProject(id,title,content,type,summarize);
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

    @RequestMapping(value = "/del",method = RequestMethod.POST)
    public JSONObject delProject(@RequestBody JSONObject data, HttpServletResponse response, HttpServletRequest request){
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
        Integer id = data.getInteger("id");
        if (projectService.getById(id) == null){
            JSONObject notExist = new JSONObject();
            notExist.put("suc",false);
            notExist.put("result","id not exist");
            return notExist;
        }
        projectService.delProject(id);
        JSONObject suc = new JSONObject();
        suc.put("suc",true);
        return suc;
    }
}
